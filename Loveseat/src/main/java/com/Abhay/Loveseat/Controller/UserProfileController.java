package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.UserDto;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class UserProfileController {
    @Autowired
    private UserServiceI userServiceI;
    @GetMapping("/home/user-profile")
    public String profile(Principal principal, Model model, HttpServletRequest request){
        String email=principal.getName();
        HttpSession session= request.getSession();
        session.setAttribute("userId",email);

        if (principal==null){
            return "login";
        }else {
            UserDto user=userServiceI.userProfile(email);
            model.addAttribute("user",user );
            return "home/userProfile";
        }
    }
    @GetMapping("/home/profile")
    public String getProfile(Model model,HttpServletRequest request){
        HttpSession session=request.getSession();
        String email=(String) session.getAttribute("userId");

        UserDto user=userServiceI.userProfile(email);
        model.addAttribute("user",user);
        return "home/editProfile";
    }
    @PostMapping("/edit-profile")
    public  String editProfile(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, HttpServletRequest request){
         HttpSession session=request.getSession();
         String email=(String) session.getAttribute("userId");

          try {
              userServiceI.saveChange(userDto,email);
              return "redirect:home/user-profile";
          }catch (DataIntegrityViolationException e){
              e.printStackTrace();
          }
          return "redirect:/profile";
    }
    @GetMapping("/home/changePassword")
    public String passwordChange(){
        return "home/changePassword";
    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("oldPassword")String oldPassword, @RequestParam("newPassword")String newPassword
                                    , RedirectAttributes redirectAttributes,HttpServletRequest httpServletRequest){

        HttpSession session= httpServletRequest.getSession();
        String email=(String) session.getAttribute("userId");
        if (userServiceI.passwordIsCorrect(oldPassword,email,newPassword)){
            return "redirect:/home/user-profile";
        }
        redirectAttributes.addFlashAttribute("message","the old password is not matching");
        return "redirect:/home/changePassword";
    }
}

