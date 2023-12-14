package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Model.Cart;
import com.Abhay.Loveseat.Model.Products;
import com.Abhay.Loveseat.Model.UserEntity;

public interface CartService {
    Cart addItemToCart(Products products, int quantity, UserEntity user);
    Cart updateItemCart(Products products,int quantity,UserEntity user);
    Cart deleteItemCart(Products products,UserEntity user);
}
