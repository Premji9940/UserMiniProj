package com.nit.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.nit.entity.UserAccount;


public interface IUserPagination extends PagingAndSortingRepository<UserAccount, Integer>{
}
