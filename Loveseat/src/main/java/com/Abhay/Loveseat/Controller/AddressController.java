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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        UserEntity user=userServiceI.findByEmail(email);
        model.addAttribute("user",user);
        return "home/manageAddress";
    }
    @GetMapping("/home/editAddress/{id}")
    public String editAddress(@PathVariable Long id, Model model){
        AddressDto address=addressServiceI.findById(id);
        model.addAttribute("address",address);
        return "home/editAddress";
    }
    @PostMapping("/home/edit-Address/{id}")
    public String saveEdit(@PathVariable Long id, @ModelAttribute("address")AddressDto addressDto,
                           RedirectAttributes redirectAttributes){
        addressServiceI.saveEdits(id,addressDto);
        redirectAttributes.addFlashAttribute("success","changes add successfully");
        System.out.println(addressDto);
        System.out.println(id);

        return "redirect:/home/manageAddress";
    }
    @PostMapping("/home/delete/{id}")
    private String deleteAddress(@PathVariable Long id ,RedirectAttributes redirectAttributes){
        addressServiceI.deleteAddress(id);
        redirectAttributes.addFlashAttribute("success","Remove successfully");
        return "redirect:/home/manageAddress";

    }

}
