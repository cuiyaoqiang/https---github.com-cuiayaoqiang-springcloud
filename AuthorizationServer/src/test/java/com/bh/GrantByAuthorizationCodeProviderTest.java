package com.bh;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtContext;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/***
 * 通过授权码方式访问受限资源
 * 
 * @author leftso
 *
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class GrantByAuthorizationCodeProviderTest extends OAuth2Test {

	//@Value("${local.server.port}")
	private int port=8082;

	@Test
	public void getJwtTokenByAuthorizationCode()
			throws JsonParseException, JsonMappingException, IOException, URISyntaxException, InvalidJwtException {

		//用户名,密码
		String userName = "leftso";
		String password = "111aaa";

		List<String> scopes = new ArrayList<String>(2);
		scopes.add("write");
		scopes.add("read");

		//步骤一：模拟返回受保护资源
		String redirectUrl = "http://localhost:" + port + "/resources/user";
		//通过用户名密码发起请求
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
		JwtContext jwtContext = jwtConsumer.process(accessToken);

		//打印出返回的授权信息
		System.out.println("-------------------------");
		logJWTClaims(jwtContext);
		System.out.println("-------------------------");

		System.out.println("第4步执行完成，http请求头中添加Authorization字段：Bearer " + accessToken+"，并分别向资源服务器发送请求");
		//组织授权后的头部
		headers = new HttpHeaders();
		//oauth2的授权访问头部类型为Bearer
		headers.set("Authorization", "Bearer " + accessToken);
		//测试访问几个授权保护的url,分别是没有权限的/client,有权限的/user,有权限的/principal,有权限的/roles
		response = new TestRestTemplate().exchange("http://localhost:" + port + "/resources/client", HttpMethod.GET,
				new HttpEntity<Object>(null, headers), String.class);
		System.out.println("client:"+response.getBody());

		response = new TestRestTemplate().exchange("http://localhost:" + port + "/resources/admin", HttpMethod.GET,
				new HttpEntity<Object>(null, headers), String.class);
		System.out.println("admin:"+response.getBody());

		response = new TestRestTemplate().exchange("http://localhost:" + port + "/resources/user", HttpMethod.GET,
				new HttpEntity<Object>(null, headers), String.class);
		System.out.println("user:"+response.getBody());

		response = new TestRestTemplate().exchange("http://localhost:" + port + "/resources/principal", HttpMethod.GET,
				new HttpEntity<Object>(null, headers), String.class);
		System.out.println("principal:"+response.getBody());

		response = new TestRestTemplate().exchange("http://localhost:" + port + "/resources/roles", HttpMethod.GET,
				new HttpEntity<Object>(null, headers), String.class);
		System.out.println("roles:"+response.getBody());

		response = new TestRestTemplate().exchange("http://localhost:" + port + "/resources/delAccount", HttpMethod.GET,
				new HttpEntity<Object>(null, headers), String.class);
		System.out.println("delAccount:"+response.getBody());

		response = new TestRestTemplate().exchange("http://localhost:" + port + "/resources/updateAccount", HttpMethod.POST,
				new HttpEntity<Object>(null, headers), String.class);
		System.out.println("updateAccount:"+response.getBody());

		response = new TestRestTemplate().exchange("http://localhost:" + 8081 + "/resources/user", HttpMethod.GET,
				new HttpEntity<Object>(null, headers), String.class);
		System.out.println("resGet:"+response.getBody());

		response = new TestRestTemplate().exchange("http://localhost:" + 8081 + "/resources/addAccount", HttpMethod.POST,
				new HttpEntity<Object>(null, headers), String.class);
		System.out.println("resAdd:"+response.getBody());

		response = new TestRestTemplate().exchange("http://localhost:" + 8081 + "/resources/updateAccount", HttpMethod.PUT,
				new HttpEntity<Object>(null, headers), String.class);
		System.out.println("resUpdate:"+response.getBody());

		response = new TestRestTemplate().exchange("http://localhost:" + 8081 + "/resources/delAccount", HttpMethod.DELETE,
				new HttpEntity<Object>(null, headers), String.class);
		System.out.println("resDelete:"+response.getBody());

	}

}
