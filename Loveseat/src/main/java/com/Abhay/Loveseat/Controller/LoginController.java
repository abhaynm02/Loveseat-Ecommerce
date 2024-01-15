package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.UserDto;
import com.Abhay.Loveseat.Model.PasswordRestToken;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Otp.OtpUtil;
import com.Abhay.Loveseat.Repository.PasswordRestTokenRepo;
import com.Abhay.Loveseat.Service.UserService;
import com.Abhay.Loveseat.Service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

@Controller
public class LoginController {
    @Autowired
    UserServiceI userService;
    @Autowired
    private OtpUtil otpUtil;
    @Autowired
    private PasswordRestTokenRepo passwordRestTokenRepo;
    @GetMapping("")
    public String home(){
        return "home/index.html";
    }

    @GetMapping("/register")
    public String register(@Param("link")String link, Model model,HttpServletRequest request) {
        HttpSession session=request.getSession();
        session.setAttribute("ReferLink",link);
        model.addAttribute("user", new UserDto());

        return "register";
    }

    @GetMapping("/otp-page")
    public String otpPage() {
        return "otp";
    }

    @PostMapping("/user-register")
    public String saveUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes, HttpServletRequest request) {
        boolean exists = userService.isEmailExists(userDto.getEmail());
        HttpSession session = request.getSession();
        String rePassword = userDto.getRepeatPassword();
        if (bindingResult.hasErrors()) {
            return "/register";
        }
        if (exists) {
            redirectAttributes.addFlashAttribute("message", "The email is exist");
            return "redirect:/register";
        }

        if (rePassword.equals(userDto.getPassword())) {
            String otp = otpUtil.generateOtp();
            System.out.println(otp+"_________");
            session.setAttribute("storeOtp", otp);
            userService.SendOpt(userDto.getEmail(), otp);
            session.setAttribute("generateTime", LocalTime.now());
            session.setAttribute("user", userDto);
            return "redirect:/otp-page";
        }

        redirectAttributes.addFlashAttribute("message", "check the password");
        return "redirect:/register";
    }

    @GetMapping("/login")
    public String login(Principal principal) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication ==null|| authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }
        String email=principal.getName();
        UserEntity user=userService.findByEmail(email);
        String role=user.getRole();
        if (role.equals("ADMIN")) {
            return "adminT/admin";

        }
        return "home/index.html";

    }

    @PostMapping("/verify")
    public String verification(@RequestParam("otp") String otp, HttpServletRequest request
            , RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        String referLink= (String)session.getAttribute("ReferLink");
        String email = userDto.getEmail();
        System.out.println(email);
        LocalTime storedTimeOtp = (LocalTime) session.getAttribute("generateTime");
        String storeOtp = (String) session.getAttribute("storeOtp");
        if (storeOtp.equals(otp) && Duration.between(storedTimeOtp,
                LocalTime.now()).getSeconds() < (2 * 60)) {
            userService.save(userDto,referLink);
            session.removeAttribute("userEmail");
            return "/login";

        }
        redirectAttributes.addFlashAttribute("message", "the otp is note correct or the otp time is out ");
        return "redirect:/otp-page";
    }

    @PostMapping("/resentOtp")
    public String resentOtp(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        String otpR = otpUtil.generateOtp();
        session.setAttribute("storeOtp", otpR);
        session.setAttribute("generateTime", LocalTime.now());
        UserDto userDto = (UserDto) session.getAttribute("user");
        String email = userDto.getEmail();
        if (email.isBlank()) {
            return "/otp-page";
        }
        System.out.println(email);
        userService.SendOpt(email, otpR);
        redirectAttributes.addFlashAttribute("message", "otp send to your email");
        return "redirect:/otp-page";
    }
    @GetMapping("/forgotPassword")
    public String forgotPassword(){

        return "forgotPassword";
    }
    @PostMapping("/forgot-Password")
    public String forgotPassword(@RequestParam("email")String email ,RedirectAttributes redirectAttributes){
        boolean exists=userService.isEmailExists(email);
        System.out.println(email);
        if (exists){
            userService.forgotPassword(email);
            redirectAttributes.addFlashAttribute("message","please check your email link send  ");


        }else {
            redirectAttributes.addFlashAttribute("message","please check your user name ");

        }
        return "redirect:/forgotPassword";

    }
    @GetMapping("/resetPassword/{token}")
    public String resetPassword(@PathVariable String token, Model model){
        PasswordRestToken rest=passwordRestTokenRepo.findByToken(token);
    if (rest!=null&& userService.hasExpipred(rest.getExpiryDateTime())){
        model.addAttribute("email",rest.getUser().getEmail());
        return  "resetPassword";
    }
    return "redirect:/login?error";

    }
    @PostMapping("/resetPassword")
    public String resetPasswordConform(@RequestParam("password") String password,@RequestParam("email")String  email){
    userService.changePassword(password,email);
        System.out.println(password +email);

    return "/login";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }

}
