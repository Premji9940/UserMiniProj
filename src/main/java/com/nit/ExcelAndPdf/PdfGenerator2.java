package com.nit.ExcelAndPdf;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Element;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nit.entity.UserAccount;

import jakarta.servlet.http.HttpServletResponse;

public class PdfGenerator2 {
public static void generate(List < UserAccount > userList, HttpServletResponse response) throws DocumentException, IOException {
    // Creating the Object of Document
    Document document = new Document(PageSize.A4);
    // Getting instance of PdfWriter
    PdfWriter.getInstance(document, response.getOutputStream());
    // Opening the created document to change it
    document.open();
    // Creating font
    // Setting font style and size
    Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
    fontTiltle.setSize(20);
    fontTiltle.setColor(Color.MAGENTA);
    
    // Creating paragraph For Heading
    Paragraph paragraph1 = new Paragraph("List of the Users", fontTiltle);
    // Aligning the paragraph in the document
    paragraph1.setAlignment(Paragraph.ALIGN_CENTER);

    paragraph1.add(new Paragraph(""));
    paragraph1.add(new Paragraph(""));
    // Adding the created paragraph in the document
    document.add(paragraph1);
   

    // Creating a table of the 9 columns
    PdfPTable table = new PdfPTable(9);
    // Setting width of the table, its columns and spacing
    table.setWidthPercentage(100f);
    table.setWidths(new int[] {2,2,2,2,2,2,2,2,2});
    table.setSpacingBefore(5);
    document.setHtmlStyleClass("bg-success");
    
    // Create Table Cells for the table header
    PdfPCell cell = new PdfPCell();
    // Setting the background color and padding of the table cell
    cell.setBackgroundColor(CMYKColor.YELLOW);
    cell.setPadding(2);
    table.setHorizontalAlignment(Element.ALIGN_MIDDLE);
    // Creating font
    // Setting font style and size
    Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
    font.setColor(CMYKColor.BLACK);
    // Adding headings in the created table cell or  header
    // Adding Cell to table
    cell.setPhrase(new Phrase("SNO", font));
   
    table.addCell(cell);
    cell.setPhrase(new Phrase("ID", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("User Name", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("Email", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("Mobile No", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("SSN", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("DOB", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("Gender", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("Status", font));
    table.addCell(cell);
    int i=1;
    // Iterating the list of students
    for ( UserAccount user: userList) {
    	table.setWidthPercentage(100);
    	table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
    	table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
    	//Adding SNo
    	table.addCell(String.valueOf(i));
      // Adding user id
      table.addCell(String.valueOf(user.getId()));
      // Adding user name
      table.addCell(user.getName());
      // Adding user mail
      table.addCell(user.getMail());
      // Adding user mobile
      table.addCell(String.valueOf(user.getMobile()));
      //Adding user ssn
      table.addCell(String.valueOf(user.getSsn()));
      //Adding user Dob
      table.addCell(String.valueOf(user.getDob()));
      //Adding Gender
      table.addCell(String.valueOf(user.getGender()));
      //Adding Status
      table.addCell(String.valueOf(user.getStatus()));

   
   i++;
    }
    // Adding the created table to the document
    document.add(table);
    // Closing the document
    document.close();
  }
}