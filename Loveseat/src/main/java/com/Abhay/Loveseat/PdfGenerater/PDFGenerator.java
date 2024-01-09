package com.Abhay.Loveseat.PdfGenerater;

import com.Abhay.Loveseat.Dto.SalesResponseDto;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {
    private List<SalesResponseDto>salesReports;

    public void setSalesReports(List<SalesResponseDto> salesReports) {
        this.salesReports = salesReports;
    }
    public void generate(HttpServletResponse response)throws DocumentException, IOException{
        Document document=new Document(PageSize.A3);

        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(18);

        Paragraph paragraph=new Paragraph("Sales Report",fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table =new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{2,5,5,2,3,3,4});
        table.setSpacingBefore(5);

        PdfPCell cell =new PdfPCell();

        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font =FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.white);

        cell.setPhrase( new Phrase("Order ID ",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("User ID",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Product Name",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Product Quantity",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total Amount",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Order Date",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Payment Method",font));
        table.addCell(cell);

         for (SalesResponseDto responseDto:salesReports){
             table.addCell(String.valueOf(responseDto.getOrderId()));
             table.addCell(String.valueOf(responseDto.getUserId()));
             table.addCell(String.valueOf(responseDto.getProductName()));
             table.addCell(String.valueOf(responseDto.getProductQuantity()));
             table.addCell(String.valueOf(responseDto.getTotalPrice()));
             table.addCell(String.valueOf(responseDto.getOrderDate()));
             table.addCell(String.valueOf(responseDto.getPaymentMethods()));

         }
         document.add(table);
         document.close();


    }
}
