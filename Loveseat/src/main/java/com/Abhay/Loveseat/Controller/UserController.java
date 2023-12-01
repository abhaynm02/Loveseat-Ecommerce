package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Model.Products;
import com.Abhay.Loveseat.Service.ProductServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private ProductServiceI productServiceI;
    @GetMapping("/home")
    public String homePage(){
        return "home/index";
    }
    @GetMapping("/shop")
    public String shop(Model model){
        List<Products>products=productServiceI.findAllProducts();
        model.addAttribute("products",products);
        return "home/shop";
    }
}
