package com.bh.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/***
 * 受保护的资源服务
 */
@RestController
public class LoginController {
	
	@Autowired
	private RestTemplate restTemplate;


	@RequestMapping(value="login", method=RequestMethod.POST)
	public Object getRoles() throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
		System.out.println("login...");
		
		int port=8761;
		String userName = "zs";
		String password = "zs";

		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		if (interceptors == null) {
			interceptors = Collections.emptyList();
		}
		interceptors = new ArrayList<ClientHttpRequestInterceptor>(interceptors);
		Iterator<ClientHttpRequestInterceptor> iterator = interceptors.iterator();
		while (iterator.hasNext()) {
			if (iterator.next() instanceof BasicAuthorizationInterceptor) {
				iterator.remove();
			}
		}
		interceptors.add(new BasicAuthorizationInterceptor(userName, password));
		restTemplate.setInterceptors(interceptors);

		String redirectUrl = "http://localhost:" + port + "/resources/user";
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port+ "/oauth/authorize?response_type=code&client_id=normal-app&redirect_uri={redirectUrl}",
				null, String.class, redirectUrl);


		List<String> setCookie = response.getHeaders().get("Set-Cookie");
		String jSessionIdCookie = setCookie.get(0);
		String cookieValue = jSessionIdCookie.split(";")[0];
		//组织一个http请求头部,放入cookie
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", cookieValue);
		System.out.println("第1步执行完成，表示申请认证，获取jsessionId："+cookieValue);

		//再次发起请求获取授权码
		response = restTemplate.postForEntity(
				"http://localhost:" + port+ "oauth/authorize?response_type=code&client_id=normal-app&redirect_uri={redirectUrl}&user_oauth_approval=true&authorize=Authorize",
				new HttpEntity<Object>(headers), String.class, redirectUrl);
		String location = response.getHeaders().get("Location").get(0);
		//获取url
		URI locationURI = new URI(location);
		//获取url后面的请求参数，即获取授权码code=xxx
		String query = locationURI.getQuery();
		System.out.println("第2步执行完成，获取授权码："+query);

		iterator = interceptors.iterator();
		while (iterator.hasNext()) {
			if (iterator.next() instanceof BasicAuthorizationInterceptor) {
				iterator.remove();
			}
		}
		interceptors.add(new BasicAuthorizationInterceptor("normal-app", ""));
		restTemplate.setInterceptors(interceptors);
		
		//组织授权码获取access token
		location = "http://localhost:" + port + "/oauth/token?" + query
				+ "&grant_type=authorization_code&client_id=normal-app&redirect_uri={redirectUrl}";
		response = restTemplate.postForEntity(location, new HttpEntity<>(new HttpHeaders()),
				String.class, redirectUrl);

		//获取access_token信息
		HashMap<?, ?> jwtMap = new ObjectMapper().readValue(response.getBody(), HashMap.class);
		String accessToken = (String) jwtMap.get("access_token");
		System.out.println("第3步执行完成，获取令牌："+accessToken);
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	}

}
