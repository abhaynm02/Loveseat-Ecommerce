package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.JsonInput;
import com.Abhay.Loveseat.Model.Cart;
import com.Abhay.Loveseat.Model.Products;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Service.CartService;
import com.Abhay.Loveseat.Service.CartServiceI;
import com.Abhay.Loveseat.Service.ProductServiceI;
import com.Abhay.Loveseat.Service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    private CartServiceI cartService;
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private ProductServiceI productServiceI;
    @GetMapping("/home/cart")
    public String cart(Model model, Principal principal ){
        UserEntity user=userServiceI.findByEmail(principal.getName());
        Cart cart=user.getCart();
        if (cart==null){
            model.addAttribute("message","No items in your cart");
        }
            model.addAttribute("cart",cart);


        return "home/cart";
    }

    @PostMapping("/add-to-cart")
    public RedirectView addProductToCart(@RequestBody JsonInput jsonInput,
                                         Principal principal,
                                         HttpServletRequest request){
        long productId= jsonInput.getProductId();
        int quantity= (int) jsonInput.getQuantity();
        Products products=productServiceI.findById(productId).orElseThrow();
        System.out.println(productId);
        System.out.println(quantity);
        UserEntity user=userServiceI.findByEmail(principal.getName());

        Cart cart=cartService.addItemToCart(products,quantity,user);
        System.out.println("the function is working properly");

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(request.getHeader("Referer"));
        return redirectView;
    }
    @PostMapping("/change-cart")
    public  String updateCart(@RequestParam("productId") Long productId,
                              @RequestParam("quantity") String quantity
                             , Model model, Principal principal, RedirectAttributes redirectAttributes,
                              HttpServletRequest request){
           int quantity1=Integer.parseInt(quantity);
           if (quantity1<0) {
               redirectAttributes.addFlashAttribute("alert","please check your quantity");
               return  "redirect:/home/cart";

           }

           UserEntity user= userServiceI.findByEmail(principal.getName());
           Products products=productServiceI.findById(productId).orElseThrow();
           Cart cart=cartService.updateItemCart(products,quantity1,user);
           Cart cart1=user.getCart();
//        model.addAttribute("cart",cart1);
        System.out.println("hii");
        return "redirect:/home/cart";
    }
    @PostMapping("/remove-item")
    public String removeItemFromCart(@RequestParam("productId")Long productId,
                                     Model model, Principal principal){
        UserEntity user=userServiceI.findByEmail(principal.getName());
        Products product=productServiceI.findById(productId).orElseThrow();
        Cart cart= cartService.deleteItemCart(product,user);
        if (cart==null){
            model.addAttribute("message","No items in your cart");
            System.out.println("heyy iam working properly");
            return "redirect:/home/cart";
        }
        model.addAttribute("cart",cart);
        return "redirect:/home/cart";
    }
}
