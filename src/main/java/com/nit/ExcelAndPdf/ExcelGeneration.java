package com.nit.ExcelAndPdf;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xddf.usermodel.text.TextAlignment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.nit.entity.UserAccount;

import jakarta.servlet.http.HttpServletResponse;

public class ExcelGeneration {
	static int i=1;
	public static void employeeReport(HttpServletResponse res,List<UserAccount> user) throws IOException {
		Workbook workbook=new XSSFWorkbook();
Sheet sheet=workbook.createSheet("hii");
		
		System.out.println(workbook.getClass()+""+sheet);
	Row s=	sheet.createRow(0);
	CellStyle cellstyle=workbook.createCellStyle();
	cellstyle.setBorderTop(BorderStyle.MEDIUM);
	cellstyle.setBorderLeft(BorderStyle.MEDIUM);
	cellstyle.setBorderRight(BorderStyle.MEDIUM);
	cellstyle.setBorderBottom(BorderStyle.MEDIUM);
	cellstyle.setAlignment(HorizontalAlignment.LEFT);

	Cell c=s.createCell(0);
	c.setCellValue("id");
	c.setCellStyle(cellstyle);
	Cell c1=s.createCell(1);

	c1.setCellValue("Name");
	c1.setCellStyle(cellstyle);

	Cell c2=s.createCell(2);
	c2.setCellValue("Mail");
	c2.setCellStyle(cellstyle);
	Cell c3=s.createCell(3);
	c3.setCellValue("Dob");
	c3.setCellStyle(cellstyle);

	Cell c4=s.createCell(4);
	c4.setCellValue("Gender");
	c4.setCellStyle(cellstyle);

	Cell c5=s.createCell(5);
	c5.setCellValue("SSN");
	c5.setCellStyle(cellstyle);

	Cell c6=s.createCell(6);
	c6.setCellValue("Mobile Number");
	c6.setCellStyle(cellstyle);

	Cell c7=s.createCell(7);
	c7.setCellValue("Status");
	c7.setCellStyle(cellstyle);

	Cell c8=s.createCell(8);
	c8.setCellValue("Creation Time");
	c8.setCellStyle(cellstyle);

	Cell c9=s.createCell(9);
	c9.setCellValue("Updation Time");
	c9.setCellStyle(cellstyle);

	
		  
		  user.forEach( user1->{
			  Row r=sheet.createRow(i);
		 Cell c11= r.createCell(0);
		 c11.setCellValue(user1.getId());
		 c11.setCellStyle(cellstyle);
		 Cell c22= r.createCell(1);
		 c22.setCellValue(user1.getName());
		 c22.setCellStyle(cellstyle);
		Cell c33=  r.createCell(2);
		c33.setCellValue(user1.getMail());
		c33.setCellStyle(cellstyle);
				//Converting LocalDate to String
		  DateTimeFormatter dtf=DateTimeFormatter.ofPattern("uuuu-MM-dd");
		  String dob=dtf.format(user1.getDob()==null?LocalDate.of(0, 1, 1):user1.getDob());
	
		Cell c44  =r.createCell(3);
		c44.setCellValue(dob);
		c44.setCellStyle(cellstyle);
		
		 Cell c55= r.createCell(4);
		 c55.setCellValue(user1.getGender());
		 c55.setCellStyle(cellstyle);
		  String ssn=String.valueOf(user1.getSsn()==null?0:user1.getSsn());
		  
		 Cell c66=r.createCell(5);
		 c66.setCellValue(ssn);
		 c66.setCellStyle(cellstyle);
		  String mobile=String.valueOf(user1.getMobile()==null?0:user1.getMobile());
//System.out.println(mobile+"\t"+user1.getMobile());
		  Cell c77=  r.createCell(6);
		  c77.setCellValue(mobile);
		  c77.setCellStyle(cellstyle);
		
				//convertin LocalDateTime to String 
				  DateTimeFormatter dateFormat= DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 
				  String
				  creationTime=dateFormat.format(user1.getCreateTime()==null?LocalDateTime.of(0, 1, 1, 0, 0):user1.getCreateTime());
	  
				  String  updateTime=dateFormat.format(user1.getUpdateTime()==null?LocalDateTime.of(0, 1, 1, 0, 0):user1.getUpdateTime());
				 
				  Cell c88=	  r.createCell(7);
				  c88.setCellValue(user1.getStatus());
				  c88.setCellStyle(cellstyle);
		  Cell c99=r.createCell(8);
		  c99.setCellValue(creationTime);
		  c99.setCellStyle(cellstyle);
		 Cell c100= r.createCell(9);
		 c100.setCellValue(updateTime);
		 c100.setCellStyle(cellstyle);
  
		  i++;
		  
		  } );
		//here work book writing the data to response object
		workbook.write(res.getOutputStream());
	}
	}
