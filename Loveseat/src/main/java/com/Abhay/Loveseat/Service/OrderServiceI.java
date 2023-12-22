package com.Abhay.Loveseat.Service;

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

import java.time.LocalDateTime;
import java.util.*;

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
    @Override
    public Orders placeOrder(long addressId, UserEntity user) {
        Address deliveryAddress = addressServiceI.findAddress(addressId);
        Cart cart = user.getCart();
        Set<CartItem> cartItems = new HashSet<>(cart.getCartItems()); // Create a copy to avoid concurrent modification

        Orders orders = new Orders();
        orders.setAddress(deliveryAddress);
        orders.setUser(user);
        orders.setOrderDate(LocalDateTime.now());

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

        orders.setOrderItems(orderItems);
        orders.setTotalAmount((float) cart.getTotalPrice());
        orders.setTotalItem((int) cart.getTotalItems());

        // Save the entire order, including associated order items
        Orders savedOrder = ordersRepository.save(orders);

        // Remove cart items after the order is successfully placed
        for (CartItem cartItem : cartItems) {
            cartServiceI.deleteItemCart(cartItem.getProduct(), user);
        }

        return savedOrder;
    }

    @Override
    public Page<Orders> findOrders(Pageable pageable, UserEntity user) {
        long id= user.getId();
        return ordersRepository.findOrders(id,pageable);
    }

    @Override
    public void cancelOrder(long orderId) {
        Optional<OrderItem> orderItem=orderItemsRepository.findById(orderId);
        OrderItem order=orderItem.get();
        order.setProductsStatus(ProductsStatus.CANCELLED);
        order.setCancelled(true);
        orderItemsRepository.save(order);
    }

    @Override
    public Page<OrderItem> getAllOrders(Pageable pageable) {
        return orderItemsRepository.findAll(pageable);
    }

    @Override
    public Optional<OrderItem> findOrderItemById(long id) {
        return orderItemsRepository.findById(id);
    }

    @Override
    public void updateOrder(long orderId, String status) {
        Optional<OrderItem> orderItem=orderItemsRepository.findById(orderId);
        OrderItem orderItem1=orderItem.get();
        orderItem1.setProductsStatus(ProductsStatus.valueOf(status));
        orderItemsRepository.save(orderItem1);
    }

    public Optional<Orders> findById(Long id) {
        return  ordersRepository.findById(id);
    }
}