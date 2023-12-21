package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Model.Cart;
import com.Abhay.Loveseat.Model.Products;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Service.CartServiceI;
import com.Abhay.Loveseat.Service.ProductServiceI;
import com.Abhay.Loveseat.Service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private ProductServiceI productServiceI;
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private CartServiceI cartServiceI;
    @GetMapping("/home")
    public String homePage(Principal principal, HttpSession session){
        UserEntity user=userServiceI.findByEmail(principal.getName());
        Cart cart=user.getCart();
        session.setAttribute("totalItems",cart.getTotalItems());

        return "home/index";
    }
    @GetMapping("/shop")
    public String shop( @RequestParam(defaultValue = "0") int page
                        ,@RequestParam(defaultValue = "12")int size,
                        Model model){
        Pageable pageable= PageRequest.of(page,size);
        Page<Products> products=productServiceI.findAllProducts(pageable);
        model.addAttribute("products",products);
        return "home/shop";
    }
    @GetMapping("/view-product/{id}")
    public String productDetail(@PathVariable("id")Long id ,Model model){
        Products products=productServiceI.findById(id).orElseThrow(()-> new IllegalArgumentException("invalid product"));
        model.addAttribute("product",products);
        return "home/productDetail";
    }
}
