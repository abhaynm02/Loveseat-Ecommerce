package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.JsonInput;
import com.Abhay.Loveseat.Enums.ProductsStatus;
import com.Abhay.Loveseat.Model.OrderItem;
import com.Abhay.Loveseat.Model.Orders;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Service.OrderServiceI;
import com.Abhay.Loveseat.Service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderServiceI orderServiceI;
    @Autowired
    private UserServiceI userServiceI;
    @GetMapping("/home/order-Success")
    public String orderSuccess(){
        return "home/thankyou";
    }
    //placing order
    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody JsonInput jsonInput , Principal principal){
         String email=principal.getName();
        UserEntity user=userServiceI.findByEmail(email);
       String  payment=jsonInput.getPaymentMethod();
        System.out.println(payment);

       orderServiceI.placeOrder(jsonInput,user);


        return new ResponseEntity<>(HttpStatus.OK);
    }
//     order history management with sorting
    @GetMapping("/home/orders")
    public String ManageOrders(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "3")int size, Model model,
                               Principal principal){
        UserEntity user=userServiceI.findByEmail(principal.getName());
        Pageable pageable= PageRequest.of(page,size, Sort.by("id").descending());
        Page<Orders>orders=orderServiceI.findOrders(pageable,user);
        model.addAttribute("orders",orders);
        return "home/manageOrders";
    }
//    order detail page show all items
    @GetMapping("/home/orderDetail/{id}")
    public  String orderDetail(@PathVariable Long id,Model model){
        Orders order=orderServiceI.findById(id).get();

        model.addAttribute("orders",order);


        return "/home/orderDetail";
    }
    @PostMapping("/home/cancelOrder")
    public ResponseEntity<String> cancelOrder( @RequestBody JsonInput jsonInput,Principal principal){
        long orderId= jsonInput.getOrderId();
        UserEntity user=userServiceI.findByEmail(principal.getName());
        System.out.println(orderId);
        orderServiceI.cancelOrder(orderId,user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/viewOrders")
   public String viewOrders( @RequestParam(defaultValue = "0")int page,
                             @RequestParam(defaultValue = "8")int size,
                             Model model){
        Pageable pageable= PageRequest.of(page,size, Sort.by("id").descending());
        Page<OrderItem>orders=orderServiceI.getAllOrders(pageable);
        model.addAttribute("order",orders);
    return "/adminT/orderManagement";
    }

    @GetMapping("/admin/order-detail/{id}")
    public String viewOrderDetail(@PathVariable long id ,Model model){
        OrderItem orderItem=orderServiceI.findOrderItemById(id).get();
        model.addAttribute("orderItem",orderItem);
        model.addAttribute("statuses", ProductsStatus.values());
        return "/adminT/viewOrder";
    }
    @PostMapping("/update-productStatus")
    public ResponseEntity<String> updateStatus(@RequestBody JsonInput jsonInput){
        long orderId= jsonInput.getOrderId();
        String status= jsonInput.getStatus();

        orderServiceI.updateOrder(orderId,status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//    user side request for returning product
    @PostMapping("/home/returnOrder")
    public ResponseEntity<String> returnRequest(@RequestBody JsonInput jsonInput){
        long orderId=jsonInput.getOrderId();
        orderServiceI.updateOrder(orderId, String.valueOf(ProductsStatus.RETURN_REQUESTED));
        System.out.println(orderId);
        System.out.println("your return request is accepted");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
