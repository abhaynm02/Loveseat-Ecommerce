package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.CategoryDto;
import com.Abhay.Loveseat.Dto.ProductsDto;
import com.Abhay.Loveseat.Model.Category;
import com.Abhay.Loveseat.Model.Products;
import com.Abhay.Loveseat.Service.CategoryServiceI;
import com.Abhay.Loveseat.Service.ProductServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
public class ProductController {
    @Autowired
    private CategoryServiceI categoryServiceI;
    @Autowired
    private ProductServiceI productServiceI;
    @GetMapping("/addProduct")
    public String addProduct(Model model){
        List<Category> categories=categoryServiceI.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("product",new ProductsDto());

        return "adminT/AddProduct";
    }
    @PostMapping("/add-product")
    public String addProduct( @ModelAttribute("product")ProductsDto productsDto, @RequestParam("image") MultipartFile multipartFile1,
                             @RequestParam("image1") MultipartFile multipartFile2, @RequestParam("image2") MultipartFile multipartFile3
                             , RedirectAttributes redirectAttributes){
                System.out.println(productsDto);

       try {
           productServiceI.save(productsDto,multipartFile1,multipartFile2,multipartFile3);
           redirectAttributes.addFlashAttribute("success","productAdd successfully");
           return "redirect:/products";
       }catch (DataIntegrityViolationException e){
           redirectAttributes.addFlashAttribute("message","the product name is already exists");
           return "redirect:/addProduct";
       }

    }

    @GetMapping("/products")
    public String AllProducts(@RequestParam(defaultValue = "0")int page,
                              @RequestParam(defaultValue = "5")int size, Model model){
        Pageable pageable= PageRequest.of(page,size);
        Page<Products> productsList=productServiceI.findAll(pageable);
        model.addAttribute("products",productsList);
        return "adminT/products";
    }
    @PostMapping("/listProduct")
    public String listProduct(@RequestParam("productId")long id){
            productServiceI.listOrUnList(id,true);
        return "redirect:/products";
    }
    @PostMapping("/unListProduct")
    public String unListProduct(@RequestParam ("productId")long id){
        productServiceI.listOrUnList(id,false);

        return "redirect:/products";
    }
    @GetMapping("/edit-product/{id}")
    public String editProduct(@PathVariable long id, Model model){
        Products products=productServiceI.findById(id).orElseThrow(()->new IllegalArgumentException("invalid category"));
        List<Category>categories=categoryServiceI.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("product",products);
        return "adminT/editProduct";
    }
    @PostMapping("/products/edit/{id}")
    public  String saveEitProduct(@PathVariable long id,@ModelAttribute ("product")Products products, @RequestParam("image") MultipartFile multipartFile1,
                                  @RequestParam("image1") MultipartFile multipartFile2, @RequestParam("image2") MultipartFile multipartFile3
            ,RedirectAttributes redirectAttributes){
        try {
            productServiceI.updateProduct(id, products, multipartFile1, multipartFile2, multipartFile3);
            redirectAttributes.addFlashAttribute("success","changes upload successfully");
        }catch (DataIntegrityViolationException e){
            redirectAttributes.addFlashAttribute("message","the product name is exists");
           e.printStackTrace();
        }

        return "redirect:/products";
    }
}
