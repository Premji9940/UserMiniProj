package com.nit.ExcelAndPdf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nit.entity.UserAccount;

import jakarta.servlet.http.HttpServletResponse;

/*
import java.awt.Color;
import java.io.IOException;
import java.util.List;

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

public class PdfGenerator {
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
    table.setHorizontalAlignment(cell.ALIGN_CENTER);
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
}*/
// use Above Class or Below

public class PdfGenerator{
	private static Font COURIER = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
	private static Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 16, Font.BOLD);
	private static Font COURIER_SMALL_FOOTER = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);

	public void generatePdfReport(HttpServletResponse res,List<UserAccount> user) throws IOException {

		Document document = new Document();

		try {
			PdfWriter.getInstance(document,res.getOutputStream() );
			document.open();
			addLogo(document);
			addDocTitle(document);
			createTable(document,9,user);
			addFooter(document);
			document.close();

		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addLogo(Document document) {
		try {	
			Image img = Image.getInstance("ashokit_logo.png");
			img.scalePercent(50f,50f);
			img.setAlignment(Element.ALIGN_RIGHT);
			document.add(img);
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addDocTitle(Document document) throws DocumentException {
		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:MM:SS"));
		Paragraph p1 = new Paragraph();
		leaveEmptyLine(p1, 1);
		p1.add(new Paragraph("UserReport", COURIER));
		
		p1.setAlignment(Paragraph.ALIGN_CENTER);
		leaveEmptyLine(p1, 1);
		p1.add(new Paragraph("Report generated on " + localDateString, COURIER_SMALL));
		p1.setAlignment(Element.ALIGN_RIGHT);

		document.add(p1);

	}

	private void createTable(Document document, int noOfColumns,List<UserAccount> user) throws DocumentException {
		Paragraph paragraph = new Paragraph();
		leaveEmptyLine(paragraph, 3);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(noOfColumns);
		String[] columnNames= {"SNO","Id","Name","Mail","Mobile","SSN","DOB","Gender","Status"};

		for(int i=0; i<noOfColumns; i++) {
			PdfPCell cell = new PdfPCell(new Phrase(columnNames[i]));
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.CYAN);
			table.addCell(cell);
		}

		table.setHeaderRows(1);
		getDbData(table,user);
		document.add(table);
	}
	
	private void getDbData(PdfPTable table,List<UserAccount> users) {
		

		int i=1;
for (UserAccount user : users) {
			
			table.setWidthPercentage(100);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			table.addCell(String.valueOf(i));
			table.addCell(String.valueOf(user.getId()));
			table.addCell(user.getName());
			table.addCell(user.getMail());

			table.addCell(String.valueOf(user.getMobile()));
			table.addCell(String.valueOf(user.getSsn()));
			table.addCell(String.valueOf(user.getDob()));
			table.addCell(user.getGender());
			table.addCell(user.getStatus());
			
			i++;

			
		}
		
	}
	
	private void addFooter(Document document) throws DocumentException {
		Paragraph p2 = new Paragraph();
		leaveEmptyLine(p2, 3);
		p2.setAlignment(Element.ALIGN_MIDDLE);
		p2.add(new Paragraph(
				"------------------------End Of " +"User Report"+"------------------------", 
				COURIER_SMALL_FOOTER));
		
		document.add(p2);
	}

	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	public String getPdfNameWithDate() {
		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:MM:SS"));
		return "UsersAccount"+"-"+localDateString+".pdf";
	}
}