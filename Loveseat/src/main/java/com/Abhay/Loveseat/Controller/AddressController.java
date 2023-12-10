package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.AddressDto;
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

@Controller
public class AddressController {
    @Autowired
    private AddressServiceI addressServiceI;
    @Autowired
    private UserServiceI userServiceI;

    @GetMapping("/home/addAddress")
    public String addAddress(Model model){
        AddressDto addressDto=new AddressDto();
        model.addAttribute("address",addressDto);
        return "home/addAddress";
    }
    @PostMapping("/saveAddress")
    public String addAddress(@Valid @ModelAttribute("address")AddressDto addressDto, BindingResult result, HttpServletRequest request){
        HttpSession session= request.getSession();
        String email= (String)session.getAttribute("userId");
        if (result.hasErrors()){
            return  "home/addAddress";
        }
        addressServiceI.save(addressDto,email);
        System.out.println(addressDto);
        return "redirect:/home/manageAddress";
    }
    @GetMapping("/home/manageAddress")
    public String mangeAddress(Model model,HttpServletRequest request){
        HttpSession session=request.getSession();
        String email=(String)session.getAttribute("userId");
        System.out.println(email);
        UserEntity user=userServiceI.findByEmail(email);
        model.addAttribute("user",user);
        return "home/manageAddress";
    }

}
