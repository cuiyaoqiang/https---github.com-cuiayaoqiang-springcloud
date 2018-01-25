package com.bh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jose4j.jwt.consumer.JwtContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bh.Application;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**简化模式（implicit grant type）不通过第三方应用程序的服务器，
 * 直接在浏览器中向认证服务器申请令牌，跳过了"授权码"这个步骤，因此得名。
 * @author bh
 *
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrantByImplicitProviderTest {

	//@Value("${local.server.port}")
	//private int port;
	private int port=8082;

	@Test
	public void getJwtTokenByImplicitGrant() throws JsonParseException, JsonMappingException, IOException {

		//用户名,密码
		String userName = "leftso";
		String password = "111aaa";

		String redirectUrl = "http://localhost:"+8082+"/resources/user";
		ResponseEntity<String> response = new TestRestTemplate(userName,password).postForEntity("http://localhost:" + port 
				+ "oauth/authorize?response_type=token&client_id=normal-app&redirect_uri={redirectUrl}", null, String.class,redirectUrl);
		List<String> setCookie = response.getHeaders().get("Set-Cookie");
		String jSessionIdCookie = setCookie.get(0);
		String cookieValue = jSessionIdCookie.split(";")[0];
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", cookieValue);
		System.out.println("第1步执行完成，表示申请认证，获取jsessionId："+cookieValue);
		response = new TestRestTemplate().postForEntity("http://localhost:" + port 
				+ "/oauth/authorize?response_type=token&client_id=normal-app&redirect_uri={redirectUrl}&user_oauth_approval=true&authorize=Authorize",
				new HttpEntity<>(headers), String.class, redirectUrl);
		String location = response.getHeaders().get("Location").get(0);
		location = location.replace("#", "?");
		System.out.println("第2步执行完成，获取access_token以及重定向地址："+location);
		response = new TestRestTemplate().getForEntity(location, String.class);
		System.out.println("第3步执行完成，从资源服务器获取请求结果："+response.getBody());
	}
}
