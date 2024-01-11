package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.OrderChartResponse;
import com.Abhay.Loveseat.Dto.PaymentSummaryDto;
import com.Abhay.Loveseat.Dto.SalesReportRequest;
import com.Abhay.Loveseat.Dto.SalesResponseDto;
import com.Abhay.Loveseat.EXCELGenerator.EXCELGenerator;
import com.Abhay.Loveseat.PdfGenerater.PDFGenerator;
import com.Abhay.Loveseat.Service.OrderServiceI;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class DashBoardController {
    @Autowired
    private OrderServiceI orderServiceI;
     private LocalDateTime startDate;
     private LocalDateTime endDate;
    @PostMapping("admin/sales-report-generate")
    @ResponseBody
    public ResponseEntity<?>findSales(@RequestBody SalesReportRequest request){
        System.out.println(request.getStartDate());
        System.out.println(request.getEndDate());
        startDate=request.getStartDate();
        endDate=request.getEndDate();
        List<SalesResponseDto>salesReport=orderServiceI.findOrderBetweenDate(request.getStartDate(),request.getEndDate());
        System.out.println(orderServiceI.orderCount());
        return new ResponseEntity<>(salesReport,HttpStatus.OK);
    }
    @GetMapping("/generate/sales-report")
    public void generateSalesPdfReport(HttpServletResponse response)throws DocumentException,IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat= new SimpleDateFormat("YYYYY-MM-DD:HH:MM:SS");
        String currentDateTime= dateFormat.format(new Date());
        String headerKey ="Content-Disposition";
        String headervalue="attachment;filename=pdf_"+currentDateTime+".pdf";
        response.setHeader(headerKey,headervalue);
        List<SalesResponseDto>saleReport=orderServiceI.findOrderBetweenDate(startDate,endDate);
        PDFGenerator generator =new PDFGenerator();
        generator.setSalesReports(saleReport);
        generator.generate(response);

    }

    @GetMapping("/generate/excel-report")
    public void generateExcelReport(HttpServletResponse response) throws IOException{
       DateFormat dateFormat = new SimpleDateFormat("YYYYY-MM-DD:HH:MM:SS");
       String fileType = "attachment; filename = sales_report"+dateFormat.format(new Date()) +".xlsx";
       response.setHeader("Content-Disposition",fileType);
       response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());
       List<SalesResponseDto>salesReport=orderServiceI.findOrderBetweenDate(startDate,endDate);
        EXCELGenerator excelGenerator = new EXCELGenerator();
        excelGenerator.setSalesReport(salesReport);
        excelGenerator.generateExel(response);
    }


    @PostMapping("admin/chart")
    public ResponseEntity<?>generateChart(){
        System.out.println("hey");
        Map<String, Map<String, Object>> salesReport1 = orderServiceI.getMonthlySalesReport(2024);
        return new ResponseEntity<>(salesReport1,HttpStatus.OK);
    }

    @GetMapping("admin/getPaymentSummary")
    public ResponseEntity<?> paymentSummery(){
        List<PaymentSummaryDto>payments=orderServiceI.getPaymentSummary();
        return new ResponseEntity<>(payments,HttpStatus.OK);
    }



    }
