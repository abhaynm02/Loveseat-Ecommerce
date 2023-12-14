package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Model.Cart;
import com.Abhay.Loveseat.Model.CartItem;
import com.Abhay.Loveseat.Model.Products;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Repository.CartItemRepository;
import com.Abhay.Loveseat.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class CartServiceI implements CartService{
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Override
    public Cart addItemToCart(Products products, int quantity, UserEntity user) {
//           check if the user have a cart
            Cart cart=user.getCart();
            if (cart==null){
//                creating a new cart for user
                cart= new Cart();
            }
        Set<CartItem> cartItems=cart.getCartItems();
            CartItem cartItem=findCartItem(cartItems, products.getId());
//            checking the user have a cartitems
            if (cartItems == null){
                cartItems=new HashSet<>();
                if (cartItem ==null){
                    cartItem =new CartItem();
                    cartItem.setProduct(products);
                    cartItem.setTotalPrice(quantity*products.getSellingPrice());
                    cartItem.setQuantity(quantity);
                    cartItem.setCart(cart);
                    cartItems.add(cartItem);
                    cartItemRepository.save(cartItem);
                }
            }else {
                if (cartItem ==null){
                    cartItem =new CartItem();
                    cartItem.setProduct(products);
                    cartItem.setTotalPrice(quantity*products.getSellingPrice());
                    cartItem.setQuantity(quantity);
                    cartItem.setCart(cart);
                    cartItems.add(cartItem);
                    cartItemRepository.save(cartItem);
                }else {
                    cartItem.setQuantity(cartItem.getQuantity()+ quantity);
                    cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * products.getSellingPrice()));
                    cartItemRepository.save(cartItem);
                }

            }
        cart.setCartItems(cartItems);
        int totalItems= totalItems(cart.getCartItems());
        double totalPrice =totalPrice(cart.getCartItems());
        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItems);
        cart.setUser(user);
        return cartRepository.save(cart);

    }

    @Override
    public Cart updateItemCart(Products products, int quantity, UserEntity user) {
        Cart cart=user.getCart();
        Set<CartItem> cartItems=cart.getCartItems();
        CartItem item=findCartItem(cartItems, products.getId());
        item.setQuantity(quantity);
        item.setTotalPrice(products.getSellingPrice()* quantity);
        cartItemRepository.save(item);
        int totalItems=totalItems(cartItems);
        double totalPrice=totalPrice(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);

        return  cartRepository.save(cart);
    }

    @Override
    public Cart deleteItemCart(Products products, UserEntity user) {
        Cart cart=user.getCart();
        Set<CartItem>cartItems= cart.getCartItems();
        CartItem item=findCartItem(cartItems,products.getId());
        cartItems.remove(item);
        cartItemRepository.delete(item);
        int totalItems=totalItems(cartItems);
        double totalPrice=totalPrice(cartItems);
        cart.setCartItems(cartItems);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItems);

        return cartRepository.save(cart);
    }

    private  CartItem findCartItem(Set<CartItem> cartItems,Long productId){

        if(cartItems== null){
            return null;
        }
        CartItem cartItem=null;
        for (CartItem item: cartItems){
            if (item.getProduct().getId() ==productId){
                cartItem=item;
            }
        }
        return cartItem;
    }
    private int totalItems(Set<CartItem> cartItems){
        int totalItems=0;
        for (CartItem item :cartItems){
            totalItems += item.getQuantity();
        }
        return totalItems;
    }
    private double totalPrice(Set<CartItem> cartItems){
        double totalPrice=0.0;
        for (CartItem item: cartItems){
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }

}
