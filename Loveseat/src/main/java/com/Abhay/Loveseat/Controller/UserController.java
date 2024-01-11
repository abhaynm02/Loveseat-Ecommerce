package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Model.Cart;
import com.Abhay.Loveseat.Model.Category;
import com.Abhay.Loveseat.Model.Products;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Service.CartServiceI;
import com.Abhay.Loveseat.Service.CategoryServiceI;
import com.Abhay.Loveseat.Service.ProductServiceI;
import com.Abhay.Loveseat.Service.UserServiceI;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private ProductServiceI productServiceI;
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private CategoryServiceI categoryServiceI;
    @Autowired
    private CartServiceI cartServiceI;
    @GetMapping("/home")
    public String homePage(Principal principal, HttpSession session){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication ==null|| authentication instanceof AnonymousAuthenticationToken){
            return "home/index";
        }
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
       List<Category> category=categoryServiceI.findAll();
        model.addAttribute("products",products);
        model.addAttribute("categories",category);
        return "home/shop";
    }
    @GetMapping("/view-product/{id}")
    public String productDetail(@PathVariable("id")Long id ,Model model){
        Products products=productServiceI.findById(id).orElseThrow(()-> new IllegalArgumentException("invalid product"));
        model.addAttribute("product",products);
        return "home/productDetail";
    }
    @GetMapping("/products/search")
    public  String productSearch(@RequestParam("searchName")String searchName,Model model,
                                 @RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "12")int size){

       Pageable pageable=PageRequest.of(page,size);
       Page<Products> products=productServiceI.searchProducts(pageable,searchName);
       List<Category>category=categoryServiceI.findAll();
        model.addAttribute("products",products);
        model.addAttribute("search",searchName);
        model.addAttribute("categories",category);

        return "home/shop";
    }
    @GetMapping("/shop/category-products")
    public String categoryFilter(@RequestParam("category")Long categoryId,Model model,
                                 @RequestParam(defaultValue ="0")int page,@RequestParam(defaultValue = "12")int size){
        Pageable pageable=PageRequest.of(page,size);
        Page<Products> products=productServiceI.filterByCategory(pageable,categoryId);
       Optional<Category> category1=categoryServiceI.findById(categoryId);
       String categoryName=category1.get().getName();
        model.addAttribute("products",products);
        model.addAttribute("categories",categoryServiceI.findAll());
        model.addAttribute("categoryName",categoryName);
        return "home/categoryFilter";
    }
}
