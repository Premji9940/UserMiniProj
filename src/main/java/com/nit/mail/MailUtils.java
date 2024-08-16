package com.nit.mail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.nit.entity.UserAccount;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailUtils {
	@Autowired JavaMailSender sender;
	@Value("${spring.mail.username}") private String fromMail;
	
	public void sendingMail(UserAccount acc,String status) throws MessagingException, IOException {
		MimeMessage createMimeMessage = sender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(createMimeMessage, true);
		
		FileReader  fr=new FileReader("mailmessage.txt");
		BufferedReader reader=new BufferedReader(fr);
		String message=reader.readLine();
		StringBuilder body=new StringBuilder();
		while(reader.read()!=-1) {
			body.append(message);
			message=reader.readLine();		
		}
		String data=body.toString();
	       data=data.replace("{name}",acc.getName() );
		data=data.replace("{status}",status );
		data=data.replace("{id}", String.valueOf(acc.getId()));
		
		helper.setFrom(fromMail);
		helper.setTo("premkumar.kalla@gmail.com");
		helper.setSubject("From spring boot mail");
		helper.setText(data,true);
		sender.send(createMimeMessage);
		
		   System.out.println(data+"\n"+fromMail);               
	

	}
	

}
