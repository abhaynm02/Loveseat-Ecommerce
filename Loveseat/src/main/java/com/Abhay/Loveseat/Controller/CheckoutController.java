package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.AddressDto;
import com.Abhay.Loveseat.Model.Cart;
import com.Abhay.Loveseat.Model.CartItem;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Service.AddressServiceI;
import com.Abhay.Loveseat.Service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class CheckoutController {
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private AddressServiceI addressServiceI;
    @GetMapping("/home/checkout")
    public String checkOutPage(Model model, Principal principal, HttpSession session, RedirectAttributes redirectAttributes){
        UserEntity user= userServiceI.findByEmail(principal.getName());
        AddressDto addressDto=new AddressDto();
        Cart cart=user.getCart();
//        adding the cart and address into the checkout page
        model.addAttribute("user",user);
        model.addAttribute("address",addressDto);
        model.addAttribute("cart",cart);
        session.setAttribute("totalItems",cart.getTotalItems());
//        preventing the user assessing checkout page  with a  empty cart
        for (CartItem item : cart.getCartItems()) {
            if (item.getQuantity() > item.getProduct().getStock()) {
                redirectAttributes.addFlashAttribute("outOfStock", "Please remove out-of-stock item");
                return "redirect:/home/cart";
            }
        }
        if (cart.getTotalItems()>=1){
            return "home/checkout";
        }
        return "redirect:/home/cart";
    }
    @PostMapping("/add-new-address")
    public String addAddress(@Valid @ModelAttribute("address")AddressDto addressDto, BindingResult result, Principal principal){

        String email= principal.getName();
        if (result.hasErrors()){
            System.out.println("hey");
            return "redirect:/home/checkout";
        }
        addressServiceI.save(addressDto,email);
        System.out.println(addressDto);
        return "redirect:/home/checkout";
    }
}
