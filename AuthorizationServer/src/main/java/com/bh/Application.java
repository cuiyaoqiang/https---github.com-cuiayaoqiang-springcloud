package com.bh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.bh.entity.Account;

@SpringBootApplication()
@EnableAutoConfiguration()
@ComponentScan(basePackages={"com.bh"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	//-----------------下面代码处理初始化一个用户-------------
	//用户名:leftso 用户密码:111aaa 用户角色:ROLE_USER

	@Autowired
	public void init(){
		Account account=new Account();
		account.setName("leftso");
		account.setPassword("111aaa");
		account.setRoles(new String []{"ROLE_USER"});
	}
}
