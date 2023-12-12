package com.Abhay.Loveseat.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    @GetMapping("/home/cart")
    public String cart(){
        return "home/cart";
    }
}
