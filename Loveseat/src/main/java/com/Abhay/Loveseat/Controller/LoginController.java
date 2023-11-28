package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.UserDto;
import com.Abhay.Loveseat.Otp.OtpUtil;
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

import java.time.Duration;
import java.time.LocalTime;

@Controller
public class LoginController {
    @Autowired
    UserServiceI userService;
    @Autowired
    private OtpUtil otpUtil;

    @GetMapping("/register")
    public String register(Model model) {
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
    public String login() {
        return "login";
    }

    @PostMapping("/verify")
    public String verification(@RequestParam("otp") String otp, HttpServletRequest request
            , RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        String email = userDto.getEmail();
        System.out.println(email);
        LocalTime storedTimeOtp = (LocalTime) session.getAttribute("generateTime");
        String storeOtp = (String) session.getAttribute("storeOtp");
        if (storeOtp.equals(otp) && Duration.between(storedTimeOtp,
                LocalTime.now()).getSeconds() < (2 * 60)) {
            userService.save(userDto);
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

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/login";
    }

}
