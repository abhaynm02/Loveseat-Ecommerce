package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.SalesReportRequest;
import com.Abhay.Loveseat.Dto.SalesResponseDto;
import com.Abhay.Loveseat.Model.OrderItem;
import com.Abhay.Loveseat.Service.OrderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DashBoardController {
    @Autowired
    private OrderServiceI orderServiceI;
    @PostMapping("admin/sales-report-generate")
    @ResponseBody
    public ResponseEntity<?>findSales(@RequestBody SalesReportRequest request){
        System.out.println(request.getStartDate());
        System.out.println(request.getEndDate());
        List<SalesResponseDto>salesReport=orderServiceI.findOrderBetweenDate(request.getStartDate(),request.getEndDate());
        for (SalesResponseDto orderItem:salesReport){
            System.out.println(orderItem);
        }

        return new ResponseEntity<>(salesReport,HttpStatus.OK);
    }
}
