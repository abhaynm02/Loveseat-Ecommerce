package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.CategoryDto;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Service.AdminService;
import com.Abhay.Loveseat.Service.CategoryServiceI;
import com.Abhay.Loveseat.Service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private AdminService adminService;

    @GetMapping("/admin")
    public String adminDashBord(){
        return "adminT/admin";
    }

    @GetMapping("/users")
    public String userList(@RequestParam(defaultValue = "0")int page,
                           @RequestParam(defaultValue = "5")int size, Model model){
        Pageable pageable = PageRequest.of(page,size);
        Page<UserEntity> UserList=adminService.userList(pageable);
        model.addAttribute("users",UserList);
        return "adminT/listOfUsers";
    }
    @PostMapping("/block")
    public String blockUser(@RequestParam ("userId") Long id){
        adminService.updateStatus(id,false);
        return "redirect:/users";
    }
    @PostMapping("/unblock")
    public String unblockUser(@RequestParam ("userId") Long id){
        adminService.updateStatus(id,true);
        return "redirect:/users";
    }
    @GetMapping("/searchUser")
    public String searchUser(@RequestParam("name") String name, Model model, @RequestParam (defaultValue = "0")int page,
                             @RequestParam(defaultValue = "5")int size, RedirectAttributes redirectAttributes){
        Pageable pageable=PageRequest.of(page,size);
        Page<UserEntity>userList=adminService.searchByName(name,pageable);
        if (userList.isEmpty()){
            redirectAttributes.addFlashAttribute("message","user not found this name");
            System.out.println("hii");
        }
        model.addAttribute("users",userList);
        return "adminT/listOfUsers";
    }

}
