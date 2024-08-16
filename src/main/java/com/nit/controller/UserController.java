package com.nit.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;
import com.nit.ExcelAndPdf.ExcelGeneration;
import com.nit.ExcelAndPdf.PdfGenerator2;
import com.nit.entity.UserAccount;
import com.nit.mail.MailUtils;
import com.nit.service.UserServiceImpl;
import com.nit.validator.UserValidate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
	
	@Autowired
	private UserServiceImpl service;
	
	@Autowired private UserValidate val;
	@GetMapping("/")
	public String getHome() {
	
		
		return "home";
	}
	//registration form lanuch
	@GetMapping("/register")
	public String formLaunch(@ModelAttribute("user") UserAccount ua,Map<String,Object> m) {
		m.put("button", "Submit");

		return "form";
	}
	
	//user Registration 
	@PostMapping("/register")
	public String formSubmit( @ModelAttribute("user") UserAccount ua,Map<String,Object> m,BindingResult br,RedirectAttributes ra) {
		
		  if(val.supports(UserAccount.class)) {
		  System.out.println("UserController.formSubmit()");
		  val.validate(ua, br);
		  
		  }
		 
		 
	
		
		  if(br.hasErrors()) {
			  System.out.println("UserController.formSubmit()==>22");
		  m.put("button", "Submit");
		  
		  return "form"; 
		  }
		  if(br.hasFieldErrors())
		  {
			  m.put("button", "Submit");
			  
			  return "form"; 		  
		  }
		 
	
	
	String msg = service.saveOrUpdateUserAccount(ua);
	ra.addFlashAttribute("msg",msg);
  
		return "redirect:/register";
	}
	
	//user retrieve Data
	@GetMapping("/all")
	public String getAllUsers(Map<String,Object> m) {
		m.put("data", service.getAllUsers());
		return "users_data";
	}
	
	//delete operation
	@GetMapping("/delete")
	public String deleteUserAccount(@RequestParam Integer id,Map<String,Object > m,RedirectAttributes ra) {
		
	boolean	delete = service.deleteUserAccount(id);
	String msg=(delete)?"User id "+id+" deleted ":"User id "+id+" is Not  Available ";
		ra.addFlashAttribute("msg",msg);
		
		return "redirect:/all";
		
	}
	
	//update operation
	@GetMapping("/edit")
	public String formLaunchForEdit(@RequestParam Integer id,Map<String,Object> m) {
		
		m.put("button", "Update");
		m.put("user", service.getOneUserAccount(id));
		return "form";
	}

	//to change active status
	
	@GetMapping("/status")
	public String changeUserStatus(@RequestParam Integer id,RedirectAttributes m) {
		UserAccount us=service.getOneUserAccount(id);
		
		if(us!=null) {
		if(service.changeUserStatus(id,us.getStatus())==1) {
		
			if(us.getStatus().equalsIgnoreCase("Active")) {
				m.addFlashAttribute ("msg", "user account has In Activated");
			}else {
				m.addFlashAttribute ("msg", "user account has  Activated");
		
			}
			return "redirect:/all";		

		}else {
			m.addFlashAttribute ("msg", "Record Not Modified");
	
		}
	}
		return "redirect:/all";		
	}

	@GetMapping("/search")
	public String searchUserByNameOrId(@RequestParam(required = false) String data,Map<String,Object> m) {
		System.out.println(data);
		
		 List<UserAccount> search=service.getbynameorid(data);
		 if(search.isEmpty()) {
			 System.out.println("UserController.searchUserByNameOrId()");
			 List<UserAccount>contain=service.getByContain(data);
			 m.put("data", contain);
			 return "users_data";

		 }
				m.put("data", service.getbynameorid(data));

		 
		
 
		return "users_data";
				
	}
	@GetMapping("/contact")
	public String getContactDetails() {
		return "contact";
	}
	
	@GetMapping("/pagination")
	public String getByUserByPagination(@RequestParam Integer pageNo,
			@RequestParam String sortDirection,@RequestParam String sortField,Map<String,Object> m ) {
		int pageSize=7;
		
		Page<UserAccount> page=service.getAllUsersByPagination(pageNo ,pageSize, sortField, sortDirection);
		m.put("totalPages", page.getTotalPages());
		m.put("totalElements", page.getTotalElements());
		m.put("currentPage", pageNo);
		m.put("sortField", sortField);
		m.put("sortDirection", sortDirection);
		m.put(sortField, page);
		m.put("reverseSortDirection", sortDirection.equalsIgnoreCase("asc")?"desc":"asc");
		m.put("data", page);
		System.out.println("hi"+page.getSort().DEFAULT_DIRECTION.name());
		//gettting Sort object using pageable object and sortiong asceding and desceding order
		//Sort hi=page.getSort().DEFAULT_DIRECTION.name().equalsIgnoreCase("ASC")?Sort.by(sortField).ascending():Sort.by(sortField).descending();
		
		
		return "pagination";
	}
	int i=0;
	@GetMapping("/excel")
	public void getExcel(HttpServletRequest req,HttpServletResponse res) throws Exception {
		List<UserAccount> user=service.getAllUsers();
		
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd:HH:MM:SS");
	String fileType="attachment;filename=employee_details_"+dateFormat.format(new Date())+".xls";
	res.setHeader("Content-Disposition", fileType);
	res.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());
	System.out.println(MediaType.APPLICATION_OCTET_STREAM.getType());
	ExcelGeneration.employeeReport(res, user);
	
	
	}
	@GetMapping("/pdf")
	public void getPdfFile(HttpServletResponse res) throws IOException, DocumentException {
	List<UserAccount> user=service.getAllUsers();
		
	
	  DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd:HH:MM:SS"); String
	  fileType="attachment;filename=employee_details_"+dateFormat.format(new
	  Date())+".pdf";
	  
	  res.setHeader("Content-Disposition", fileType);
	  res.setContentType(MediaType.APPLICATION_PDF_VALUE);
	  PdfGenerator2.generate(user, res);
	 
		/*
		 * PdfGenerator pdf=new PdfGenerator();
		 * 
		 * 
		 * res.setHeader("Content-Disposition",
		 * "attachment;filename="+pdf.getPdfNameWithDate()); //it makes downloading the
		 * file res.setContentType(MediaType.APPLICATION_PDF_VALUE); //it makes the
		 * content showing on the browser screen based on content type
		 * System.out.println(MediaType.APPLICATION_PDF_VALUE);
		 * pdf.generatePdfReport(res, user);
		 */	

		
	}
}
