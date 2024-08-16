package com.nit.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.hibernate.cfg.PkDrivenByDefaultMapsIdSecondPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nit.entity.UserAccount;
import com.nit.mail.MailUtils;
import com.nit.repo.IUserPagination;
import com.nit.repo.IUserRepo;

import jakarta.mail.MessagingException;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserRepo repo;
	@Autowired
	private IUserPagination prepo;
	
	@Autowired private MailUtils utils;

	@Override
	public String saveOrUpdateUserAccount(UserAccount ua) {
		
		UserAccount user=null;
		Integer id = ua.getId();
		if (id == null) {
			// user save operation
		user = repo.save(ua);
			if(user!=null) {
				try {
					utils.sendingMail(user,"Registered");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return "user is successfully registered with" + user.getId() ;
		} else {
			try {
				utils.sendingMail(ua,"Updated");
				
			} catch (MessagingException e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// user update operation
		
			return "user is successfully Updated with" + repo.save(ua).getId();

		}

	}


	@Override
	public boolean deleteUserAccount(Integer id) {
		UserAccount result=getOneUserAccount(id);	
		if (result!=null) {
			repo.deleteById(id);
			

			return true;
		}
		return false;
	}

	@Override
	public int changeUserStatus(Integer id,String status) {
		UserAccount ac=repo.findById(id).get();

		if(status.equalsIgnoreCase("Active")) {
		/*	try {
				utils.sendingMail(ac,"DeActivated");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/
			return repo.softDeletion(id, "InActive");
	
		}else {
			/*try {
				utils.sendingMail(ac,"Activated");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/
			return repo.softDeletion(id, "Active");
	
		}	
	}

	// To Retrieve all Users
	@Override
	public List<UserAccount> getAllUsers() {

		return repo.findAll();
	}

	@Override
	public UserAccount getOneUserAccount(Integer id) {
		Optional<UserAccount> user = repo.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}


	


@Override
public Page<UserAccount> getAllUsersByPagination(int pageNo, int pageSize, String sortField, String sortDirection) {
	System.out.println("ff"+Sort.Direction.ASC.name());//her weget direction is  asc or desc 
Sort sort=	 sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending():Sort.by(sortField).descending();
Pageable pr=PageRequest.of(pageNo-1, pageSize, sort);
return prepo.findAll(pr);
}


@Override
public List<UserAccount> getByContain(String name) {
	System.out.println(name);
	
	return repo.searchByContaines(name);
}


@Override
public List<UserAccount> getbynameorid(String name) {
	
	return repo.searchByNameOrId(name,name,name,name,name,name);
}


}
