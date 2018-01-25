package com.bh.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bh.entity.AccountEntity;
import com.bh.services.AccountService;

@RestController //返回json字符串的数据，直接可以编写RESTFul的接口；
@RequestMapping("/account")
public class AccountResource {
	
	@Autowired
	private AccountService accountSer;
	
	@GetMapping(value="/findAll")
	public List<AccountEntity> findAllAccount(HttpServletRequest request,HttpServletResponse response){
		List<AccountEntity> accountEntities = accountSer.findAll();
		System.out.println(accountEntities);
		
		Cookie cookie = new Cookie("key","value");
		cookie.setMaxAge(60*60*24); 
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		return accountEntities;
	}
	@RequestMapping("/zeroException")
    public int zeroException(){
       return 100/0;
    }
}