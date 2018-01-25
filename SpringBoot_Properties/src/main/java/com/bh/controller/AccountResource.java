package com.bh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bh.entity.AccountEntity;
import com.bh.services.AccountService;

/**Spring Boot也是引用了JSON解析包Jackson,使用@RestController返回json
 * @author bh
 *
 */
@RestController //返回json字符串的数据，直接可以编写RESTFul的接口；
@RequestMapping("/account")
public class AccountResource {
	
	@Autowired
	private AccountService accountSer;
	
	@Value("${filePathLocation}")
	private String filePathLocation;
	@Value("${filePathLocation2}")
	private String filePathLocation2;
	
	//@GetMapping(value="/accounts")
	@RequestMapping(value="/accounts", method=RequestMethod.GET)
    @ResponseBody
	public List<AccountEntity> findAllAccount(){
		
		System.out.println("filePathLocation:"+filePathLocation);
		System.out.println("filePathLocation2:"+filePathLocation2);
		List<AccountEntity> accountEntities = accountSer.findAll();
		System.out.println(accountEntities);
		return accountEntities;
	}
	@RequestMapping("/zeroException")
    public int zeroException(){
		
       return 100/0;
    }
}