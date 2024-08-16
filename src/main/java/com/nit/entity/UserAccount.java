package com.nit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
//this execute only when we perform delete operation using repository method
//@SQLDelete(sql = "update user_account set status='inactive' where id=?")
//@where is implicit condition it is implicitly added to every query 
//@Where(clause = "status != 'InActive'")
public class UserAccount {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String mail;
	@DateTimeFormat(pattern = "yyyy-MM-dd")// by default spring boot convert date pattern according to date classes patterns 
	//but while diplaying the date in ui page it is does not convert required pattern of date component so date is does not populate on ui page
	//for  This annotation is very use full to dispaly data pattern in our choice pattern in the ui page
	private LocalDate dob;

	private Long mobile;
	private String gender;
	private Long ssn;
	@CreationTimestamp
	@Column(updatable =false  ) 
	//updatable=false means .this column does not participate in update/edit operation
	//while doing updation operation it does participate.
	private LocalDateTime createTime;
	@UpdateTimestamp
	@Column(insertable=false)
	//insertable=false means this column or property filed doesn't particpate in insert mode
	private LocalDateTime updateTime;
	private String status = "Active";

}
