package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.JsonInput;
import com.Abhay.Loveseat.Dto.OrderChartResponse;
import com.Abhay.Loveseat.Dto.PaymentSummaryDto;
import com.Abhay.Loveseat.Dto.SalesResponseDto;
import com.Abhay.Loveseat.Enums.PaymentMethods;
import com.Abhay.Loveseat.Enums.ProductsStatus;
import com.Abhay.Loveseat.Model.*;
import com.Abhay.Loveseat.Repository.CartItemRepository;
import com.Abhay.Loveseat.Repository.CartRepository;
import com.Abhay.Loveseat.Repository.OrderItemsRepository;
import com.Abhay.Loveseat.Repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceI implements OrderService {
    @Autowired
    private AddressServiceI addressServiceI;
    @Autowired
    private ProductServiceI productServiceI;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartServiceI cartServiceI;
    @Autowired
    private WalletServiceI walletServiceI;
    @Autowired
    private  CouponServiceI couponServiceI;
    @Override
    public Orders placeOrder(JsonInput jsonInput, UserEntity user) {
        long addressId=(long)jsonInput.getAddressId();
        long couponId=(long)jsonInput.getCouponId();
        Address deliveryAddress = addressServiceI.findAddress(addressId);
        Cart cart = user.getCart();
        Set<CartItem> cartItems = new HashSet<>(cart.getCartItems()); // Create a copy to avoid concurrent modification

        Orders orders = new Orders();
        orders.setAddress(deliveryAddress);
        orders.setUser(user);
        orders.setOrderDate(LocalDateTime.now());
        orders.setPaymentMethods(PaymentMethods.valueOf(jsonInput.getPaymentMethod()));

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProducts(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice((float) item.getTotalPrice());
            orderItem.setProductsStatus(ProductsStatus.PENDING);
             //stock management
          productServiceI.manageStock(item.getProduct(),item.getQuantity());
            // Set the association in both directions
            orderItem.setOrders(orders);
            orderItems.add(orderItem);
        }
      double couponDiscount=0;
        if (couponId != 0){
//            coupon management
            couponDiscount=couponServiceI.calculateCouponDiscount(couponId,cart.getTotalPrice());
             couponServiceI.couponStockManagement(couponId);
            //if payment is wallet we want to reduce the amount in wallet
            if (jsonInput.getPaymentMethod().equals("WALLET")){
                walletServiceI.walletPaymentInOrder(couponDiscount,user);
            }
        }else {
            if (jsonInput.getPaymentMethod().equals("WALLET")){
                walletServiceI.walletPaymentInOrder(cart.getTotalPrice(),user);
            }
        }


        orders.setOrderItems(orderItems);
        orders.setTotalAmount((float) cart.getTotalPrice());
        orders.setTotalItem((int) cart.getTotalItems());
        orders.setOfferPrice((float) couponDiscount);
        // Save the entire order, including associated order items
        Orders savedOrder = ordersRepository.save(orders);

        // Remove cart items after the order is successfully placed
        for (CartItem cartItem : cartItems) {
            cartServiceI.deleteItemCart(cartItem.getProduct(), user);
        }

        return savedOrder;
    }
//finding order for specified user
    @Override
    public Page<Orders> findOrders(Pageable pageable, UserEntity user) {
        long id= user.getId();
        return ordersRepository.findOrders(id,pageable);
    }
//   canceling order from userSide
    @Override
    public void cancelOrder(long orderId,UserEntity user) {
        Optional<OrderItem> orderItem=orderItemsRepository.findById(orderId);
        OrderItem order=orderItem.get();
        Orders orders=order.getOrders();
        order.setProductsStatus(ProductsStatus.CANCELLED);
        order.setCancelled(true);
        orderItemsRepository.save(order);
//        amount refund if the payment method is wallet or online
        if (orders.getPaymentMethods()==PaymentMethods.valueOf("WALLET")||
                orders.getPaymentMethods()==PaymentMethods.valueOf("ONLINE_PAYMENT")){
            walletServiceI.createOrUpdateWallet(user,order.getTotalPrice());
        }

        productServiceI.updateStockAfterCancellation(order.getProducts(),order.getQuantity());
    }
//finding all orders
    @Override
    public Page<OrderItem> getAllOrders(Pageable pageable) {
        return orderItemsRepository.findAll(pageable);
    }
//finding orderItem  by id for a specific item
    @Override
    public Optional<OrderItem> findOrderItemById(long id) {
        return orderItemsRepository.findById(id);
    }

//updating order status
    @Override
    public void updateOrder(long orderId, String status) {
        Optional<OrderItem> orderItem=orderItemsRepository.findById(orderId);
        OrderItem orderItem1=orderItem.get();
        UserEntity user=orderItem1.getOrders().getUser();
        orderItem1.setProductsStatus(ProductsStatus.valueOf(status));
        if (status.equals("RETURNED")){
            walletServiceI.createOrUpdateWallet(user,orderItem1.getTotalPrice());
            productServiceI.updateStockAfterCancellation(orderItem1.getProducts(),orderItem1.getQuantity());
        }
        orderItemsRepository.save(orderItem1);
    }

    @Override
    public List<SalesResponseDto> findOrderBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<OrderItem> orderItems = orderItemsRepository.findByOrderDateBetween(startDate, endDate);
        List<SalesResponseDto> orderItemList = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            SalesResponseDto responseDto = new SalesResponseDto();

            responseDto.setOrderId(orderItem.getId());
            responseDto.setUserId(orderItem.getOrders().getUser().getEmail());
            responseDto.setProductName(orderItem.getProducts().getName());
            responseDto.setProductQuantity(orderItem.getQuantity());
            responseDto.setTotalPrice(orderItem.getTotalPrice());
            responseDto.setOrderDate(orderItem.getOrders().getOrderDate());
            responseDto.setPaymentMethods(orderItem.getOrders().getPaymentMethods());

            orderItemList.add(responseDto);
        }

        return orderItemList;
    }


    public long orderCount(){
        return ordersRepository.countOrdersByMonth(LocalDateTime.now());
    }

    public Map<String, Map<String, Object>> getMonthlySalesReport(int year) {
        List<OrderItem> orderItems = orderItemsRepository.findAll();
        Map<String, Map<String, Object>> monthlySalesReport = new LinkedHashMap<>();

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (String month : months) {
            String monthYear = month + " " + year;
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("orderCount", 0);
            monthData.put("totalAmount", 0.0f);
            monthlySalesReport.put(monthYear, monthData);
        }

        for (OrderItem orderItem : orderItems) {
            LocalDate orderDate = orderItem.getOrders().getOrderDate().toLocalDate();
            if (orderDate.getYear() != year) {
                continue;
            }

            String monthYear = orderDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"));

            if (monthlySalesReport.containsKey(monthYear)) {
                Map<String, Object> monthData = monthlySalesReport.get(monthYear);
                int orderCount = (int) monthData.get("orderCount");
                float totalAmount = (float) monthData.get("totalAmount");

                monthData.put("orderCount", orderCount + 1);
                monthData.put("totalAmount", totalAmount + orderItem.getTotalPrice());
            }
        }

        return monthlySalesReport;
    }


    public List<PaymentSummaryDto> getPaymentSummary() {
        List<Object[]> results=ordersRepository.getPaymentSummary();

        return results.stream().map(result ->new PaymentSummaryDto(
                (PaymentMethods) result[0],
                (Long) result[1],
                (Double) result[2]
        ))
                .collect(Collectors.toList());
    }

    public Optional<Orders> findById(Long id) {
        return  ordersRepository.findById(id);
    }

}

