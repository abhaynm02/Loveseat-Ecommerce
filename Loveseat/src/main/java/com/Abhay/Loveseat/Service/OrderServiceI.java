package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.AddressDto;
import com.Abhay.Loveseat.Model.*;
import com.Abhay.Loveseat.Repository.CartItemRepository;
import com.Abhay.Loveseat.Repository.CartRepository;
import com.Abhay.Loveseat.Repository.OrderItemsRepository;
import com.Abhay.Loveseat.Repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceI implements OrderService {
    @Autowired
    private AddressServiceI addressServiceI;
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
            System.out.println(cartItem);
        }

        return savedOrder;
    }

}
