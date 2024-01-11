package com.Abhay.Loveseat.EXCELGenerator;

import com.Abhay.Loveseat.Dto.SalesResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class EXCELGenerator  {
    private List<SalesResponseDto>salesReport;


    public void setSalesReport(List<SalesResponseDto> salesReport) {
        this.salesReport = salesReport;
    }
    public void generateExel(HttpServletResponse response){
        try(Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sales Report Excel");

            CellStyle cellStyle = workbook.createCellStyle();

            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderRight(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
            cellStyle.setBorderLeft(BorderStyle.MEDIUM);
            cellStyle.setAlignment(HorizontalAlignment.LEFT);

            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("Order Id");
            cell.setCellStyle(cellStyle);

            Cell cell1 =row.createCell(1);
            cell1.setCellValue("User Id");
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Product Name");
            cell2.setCellStyle(cellStyle);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue("Product Quantity");
            cell3.setCellStyle(cellStyle);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("Total Amount");
            cell4.setCellStyle(cellStyle);

            Cell cell5 = row.createCell(5);
            cell5.setCellValue("Order Date");
            cell5.setCellStyle(cellStyle);

            Cell cell6 = row.createCell(6);
            cell6.setCellValue("Payment Method");
            cell6.setCellStyle(cellStyle);
            int rowNum =1;
            for (SalesResponseDto salesReport :salesReport){
                Row salesRow =sheet.createRow(rowNum++);

                Cell orderIdCell = salesRow.createCell(0);
                orderIdCell.setCellStyle(cellStyle);
                orderIdCell.setCellValue(salesReport.getOrderId());

                Cell userId =salesRow.createCell(1);
                userId.setCellValue(salesReport.getUserId());
                userId.setCellStyle(cellStyle);

                Cell productNameCell =salesRow.createCell(2);
                productNameCell.setCellStyle(cellStyle);
                productNameCell.setCellValue(salesReport.getProductName());

                Cell productQuantityCell = salesRow.createCell(3);
                productQuantityCell.setCellValue(salesReport.getProductQuantity());
                productQuantityCell.setCellStyle(cellStyle);

                Cell totalAmountCell = salesRow.createCell(4);
                totalAmountCell.setCellStyle(cellStyle);
                totalAmountCell.setCellValue(salesReport.getTotalPrice());

                Cell orderDateCell = salesRow.createCell(5);
                orderDateCell.setCellStyle(cellStyle);
                orderDateCell.setCellValue(salesReport.getOrderDate());

                Cell paymentMethod =salesRow.createCell(6);
                paymentMethod.setCellStyle(cellStyle);
                paymentMethod.setCellValue(String.valueOf(salesReport.getPaymentMethods()));
            }
            workbook.write(response.getOutputStream());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
