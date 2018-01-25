package com.yihaomen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yihaomen.model.UserInfo;
import com.yihaomen.service.UserInfoService;


@RestController
@RequestMapping("/users")
public class UserInfoController {
	
	@Autowired	
	private UserInfoService userInfoService;
	
	@RequestMapping(value="",method = RequestMethod.GET)
	public List<UserInfo> findUsers(){		
		return userInfoService.findAllUsers();
	}
	
	@RequestMapping(value="/create",method = RequestMethod.GET)
	public UserInfo createUsers(){		
		return userInfoService.createUser();
	}

}
