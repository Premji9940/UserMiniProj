package com.nit.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nit.entity.UserAccount;


@Component
public class UserValidate implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		System.out.println("UserValidate.supports()");
		return clazz.isAssignableFrom(UserAccount.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("UserValidate.validate()");
		UserAccount ua=(UserAccount) target;
		String mail=ua.getMail();
		if(ua.getName().isBlank() || ua.getName().isEmpty()|| ua.getName()==null) {
			errors.rejectValue("name","","namerequired");
		}else if(ua.getName().length()<=4 ) {
			errors.rejectValue("name","","Name must be greater 4 letters");

		}
		if(mail==null|| mail.isBlank()|| mail.isBlank()) {
			errors.rejectValue("mail", "mail should not be empty","mail empty");
		}else if(mail.length()<3) {
			errors.rejectValue("mail", "mail above 3 letters","enter above 4 letters");
			
		}
		
		if(ua.getDob()==null) {
			errors.rejectValue("dob", "","select date");
		}
		if(ua.getMobile()==null ) {
			errors.rejectValue("mobile", "","Mobile Required");		
		}
		
		if(!errors.hasFieldErrors("ssn"))
		{
			if(ua.getSsn()==null ) {
				errors.rejectValue("ssn", "","SSN Required");

			}
		}
		if(ua.getGender()==null || ua.getGender().isBlank() ||ua.getGender().isEmpty()) {
			errors.rejectValue("gender", "","select gender");

		}
	
		
		
	}
}
