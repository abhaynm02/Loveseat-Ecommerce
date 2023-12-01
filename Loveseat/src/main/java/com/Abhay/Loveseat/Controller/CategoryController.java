package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.CategoryDto;
import com.Abhay.Loveseat.Model.Category;
import com.Abhay.Loveseat.Service.CategoryServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoryController {
    @Autowired
    private CategoryServiceI categoryServiceI;
    @GetMapping("/addCategory")
    public String addCategoryAdmin( Model model){
        model.addAttribute("category",new CategoryDto());
        return "adminT/categoryAdd";
    }
    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("category") CategoryDto categoryDto , RedirectAttributes redirectAttributes){
        try {
            categoryServiceI.saveCategory(categoryDto);
        }catch (DataIntegrityViolationException e){
            redirectAttributes.addFlashAttribute("message","the category name is already exists");

            e.printStackTrace();
            return "redirect:/addCategory";
        }


        return "redirect:/categories";
    }
    @GetMapping("/categories")
    public String categoryList(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "5")int size,Model model){
        Pageable pageable= PageRequest.of(page,size);
        Page<Category> categoryList=categoryServiceI.AllCategory(pageable);
        model.addAttribute("categories",categoryList);
        return  "adminT/category";
    }
    @PostMapping("/listCategory")
    public String listCategory(@RequestParam("userId") long id){
        categoryServiceI.ListOrUnList(id,true);
        System.out.println(id);
        return "redirect:/categories";
    }
    @PostMapping("/unListCategory")
    public  String unListCategory(@RequestParam("userId") long id){
        categoryServiceI.ListOrUnList(id,false);
        return "redirect:/categories";
    }
    @GetMapping("/edit-category/{id}")
    public String showEditForm(@PathVariable Long id,Model model){
        Category category=categoryServiceI.findById(id)
                .orElseThrow(()->new IllegalArgumentException("invalid category"));
        model.addAttribute("category",category);
        return "adminT/editCategory";
    }
    @PostMapping("/categories/edit/{id}")
    public  String editCategory(@PathVariable Long id,@ModelAttribute("category") Category category,
                                RedirectAttributes redirectAttributes){

        try {
            categoryServiceI.editCategory(id,category);
            redirectAttributes.addFlashAttribute("success","Edited successfully");
        }catch (DataIntegrityViolationException exception){
            redirectAttributes.addFlashAttribute("message","the category name is already exists");
            exception.printStackTrace();
            return "redirect:/categories";
        }
        return "redirect:/categories";
    }

}
