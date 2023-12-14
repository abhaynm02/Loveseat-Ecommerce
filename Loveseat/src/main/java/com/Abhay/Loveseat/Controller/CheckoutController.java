package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.AddressDto;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class CheckoutController {
    @Autowired
    private UserServiceI userServiceI;
    @GetMapping("/home/checkout")
    public String checkOutPage(Model model, Principal principal){
        UserEntity user= userServiceI.findByEmail(principal.getName());
        AddressDto addressDto=new AddressDto();
        model.addAttribute("user",user);
        model.addAttribute("address",addressDto);
        model.addAttribute("cart",user.getCart());
        return "home/checkout";
    }
}
