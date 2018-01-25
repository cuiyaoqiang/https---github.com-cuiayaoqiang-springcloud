package com.bh.dao;  


import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.bh.entity.Account;  

@Repository
public class AccountDao{  

 
    @PreAuthorize("hasRole('ROLE_USER')")  
	public void updateAccount() {  
		System.out.println("ROLE_USER updateAccount success");  
	}  

	@PostAuthorize ("returnObject.type == authentication.name")  
	public Account findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

    @PreAuthorize("hasRole('ROLE_USER') AND hasRole('ROLE_ADMIN')")  
	public void deleteUser() {
		System.out.println("ROLE_USER AND hasRole ROLE_ADMIN");
	}  
}  