package com.nit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.nit.entity.UserAccount;

public interface IUserService {

	public String saveOrUpdateUserAccount(UserAccount ua);


	public boolean deleteUserAccount(Integer id);


	public List<UserAccount> getAllUsers();

	public UserAccount getOneUserAccount(Integer id);


	int changeUserStatus(Integer id, String status);

	public List<UserAccount> getByContain(String name);
	public Page<UserAccount> getAllUsersByPagination(int pageNo, int pageSize, String sortField, String sortDirection);
	
	public List<UserAccount> getbynameorid(String name);
}
