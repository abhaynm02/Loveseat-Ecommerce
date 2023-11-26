package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.UserDto;
import com.Abhay.Loveseat.Service.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @Autowired
    UserServiceI userService;
    @GetMapping("/register")
    public  String register(Model model){
        model.addAttribute("user",new UserDto());

        return "register";
    }
    @GetMapping("/otp-page")
    public String otpPage(){
        return "otp";
    }
    @PostMapping("/user-register")
    public String saveUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes , HttpServletRequest request){
        boolean exists= userService.isEmailExists(userDto.getEmail());
        HttpSession session= request.getSession();
        String rePassword=userDto.getRepeatPassword();
        if (bindingResult.hasErrors()){
            return "/register";
        }
        if (exists){
            redirectAttributes.addFlashAttribute("message","The email is exist");
            return "redirect:/register";
        }

        if (rePassword.equals(userDto.getPassword())){
            userService.save(userDto);
            session.setAttribute("userEmail",userDto.getEmail());
            return "redirect:/otp-page";
        }

        redirectAttributes.addFlashAttribute("message","check the password");
        return "redirect:/register";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/verify")
    public  String verification(@RequestParam("otp")String otp, HttpServletRequest request
            , RedirectAttributes redirectAttributes){
        HttpSession session=request.getSession();
        String email=(String) session.getAttribute("userEmail");
        System.out.println(email);
        boolean verify =userService.verifyAccount(email,otp);
        if (verify){
            session.removeAttribute("userEmail");
            return "/login";

        }
        redirectAttributes.addFlashAttribute("message","the otp is note correct or the otp time is out ");
        return "redirect:/otp-page";
    }
    @PostMapping("/resentOtp")
    public String resentOtp(HttpServletRequest request,RedirectAttributes redirectAttributes){
        HttpSession session=request.getSession();
        String email=(String) session.getAttribute("userEmail");
        if (email.isBlank()){
            return "/otp-page";
        }
        System.out.println(email);
        userService.resentOtp(email);
        redirectAttributes.addFlashAttribute("message","otp send to your email");
        return "redirect:/otp-page";
    }

}
