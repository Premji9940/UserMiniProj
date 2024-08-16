package com.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nit.entity.UserAccount;

import jakarta.transaction.Transactional;

public interface IUserRepo extends JpaRepository<UserAccount, Integer>{
    @Modifying
    @Transactional
    @Query("update UserAccount set status=:status where id=:id")
	public int softDeletion(Integer id,String status);
    
    @Query(" from UserAccount where name like %:name%")   
    public List<UserAccount> searchByContaines(String name);
    
    
    @Query(nativeQuery = true,value = "select * from user_Account where id=? or name=? or mail=? or gender=? or mobile=? or ssn=?")   
    public List<UserAccount> searchByNameOrId(String id,String name,String mail,String gender,String mobile,String ssn);
    
   
}
