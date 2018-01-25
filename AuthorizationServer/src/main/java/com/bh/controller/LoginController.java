package com.bh.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/***
 * 受保护的资源服务
 * @author leftso
 */
@RestController
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(method=RequestMethod.GET)
	public String login(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse) throws URISyntaxException, JsonParseException, JsonMappingException, IOException {
		//用户名,密码
		String userName = "leftso";
		String password = "111aaa";
		int port =8082;
		//步骤一：模拟返回受保护资源
		//通过用户名密码发起请求
		String redirectUrl = "http://localhost:" + port;

		ResponseEntity<String> response = new TestRestTemplate(userName,password).postForEntity(
				"http://localhost:" + port+ "/oauth/authorize?response_type=code&client_id=normal-app&redirect_uri={redirectUrl}",
				null, String.class, redirectUrl);
		//获取cookie里面的JSSIONID cookie
		List<String> setCookie = response.getHeaders().get("Set-Cookie");
		String jSessionIdCookie = setCookie.get(0);
		String cookieValue = jSessionIdCookie.split(";")[0];
		//组织一个http请求头部,放入cookie
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", cookieValue);
		System.out.println("第1步执行完成，表示申请认证，获取jsessionId："+cookieValue);
		//再次发起请求获取授权码
		response = new TestRestTemplate().postForEntity(
				"http://localhost:" + port+ "oauth/authorize?response_type=code&client_id=normal-app&redirect_uri={redirectUrl}&user_oauth_approval=true&authorize=Authorize",
				new HttpEntity<Object>(headers), String.class, redirectUrl);
		String location = response.getHeaders().get("Location").get(0);
		//获取url
		URI locationURI = new URI(location);
		//获取url后面的请求参数，即获取授权码code=xxx
		String query = locationURI.getQuery();
		System.out.println("第2步执行完成，获取授权码："+query);

		//组织授权码获取access token
		location = "http://localhost:" + port + "/oauth/token?" + query
				+ "&grant_type=authorization_code&client_id=normal-app&redirect_uri={redirectUrl}";
		response = new TestRestTemplate("normal-app", "").postForEntity(location, new HttpEntity<>(new HttpHeaders()),
				String.class, redirectUrl);

		//获取access_token信息
		HashMap<?, ?> jwtMap = new ObjectMapper().readValue(response.getBody(), HashMap.class);
		String accessToken = (String) jwtMap.get("access_token");
		System.out.println("第3步执行完成，获取令牌："+accessToken);
		
		Cookie cookie = new Cookie("accessToken", accessToken);
		cookie.setMaxAge(10); //设置cookie的过期时间是10s
		httpServletResponse.addCookie(cookie);

		return "success";
	}
}
