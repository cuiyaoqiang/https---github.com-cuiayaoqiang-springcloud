package com.bh.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bh.common.HttpUtil;
import com.bh.common.JwtUtil;
import com.bh.common.UrlProperties;
import com.bh.entity.AccountEntity;
import com.bh.entity.AccountServer;
import com.bh.entity.FunctionPointEntity;
import com.bh.entity.MenuEntity;
import com.bh.entity.PermissionEntity;
import com.bh.service.AccountSer;
import com.bh.service.FunctionPointSer;
import com.bh.service.MenuSer;
import com.bh.service.PermissionSer;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/account")
public class AccountResource {

	@Autowired
	private AccountSer accountSer;
	@Autowired
	private AccountServer accountServer;
	@Autowired
	private PermissionSer permissionService;
	@Autowired
	private FunctionPointSer functionPointService;
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UrlProperties urlpro;

	@Autowired
	private MenuSer menuService;

	@GetMapping(value="/findAll")
	public List<Map<String,String>> findAllAccount(){
		List<Map<String,String>> accountEntities = accountSer.findAll();
		return accountEntities;
	}
	@GetMapping(value="/findByItself")
	public List<Map<String,String>> findByItself(HttpServletRequest httpServletRequest){
		JSONObject json = jwtUtil.getJwtjsonByRequire(httpServletRequest);
		String account =  (String) json.get("userName");
		System.out.println("account··············"+account);
		return accountSer.findByItself(account);
	}
	/*
	 * 查询单个账号
	 */
	@GetMapping(value="/toUpdate/{account}")
	public Map<String,Object> findByAccount(@PathVariable("account") String account){
		return accountSer.findByAccount(account);
	}

	/*
	 * 验证账号是否存在
	 */
	@GetMapping(value="/verification/{account}")
	public String findByAccountVerification(@PathVariable("account") String account){
		return accountSer.findByAccountVerification(account);
	}
	/**
	 * 新增界面数据
	 * @return
	 */
	@GetMapping(value="/toAdd")
	public Map<String,Object> getAdd(){
		String str = HttpUtil.get(urlpro.getUrl()+ "/HrMicroService/organ/cas");
		List<Map<String,Object>> perdata = new Gson().fromJson(str,new TypeToken<List<Map<String,Object>>>() {}.getType());
		List<Map<String,String>> result = accountSer.findRole();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("data",result);
		resultMap.put("type","ADD");
		resultMap.put("perdata",perdata);
		return resultMap;
	}
	/**
	 * 
	 * @param accountEntity
	 * @return
	 */
	@PostMapping(value="/add")
	public String addAccount(@RequestBody AccountEntity accountEntity){
		return accountSer.addAccount(accountEntity);
	}

	@PutMapping(value="/update")
	public String updateAccount(@RequestBody AccountEntity accountEntity){
		return accountSer.updateAccount(accountEntity);
	}


	public OAuth2RestOperations restTemplate() {
		AccessTokenRequest atr = new DefaultAccessTokenRequest();
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext(atr));
		ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
		template.setAccessTokenProvider(provider);
		return template;
	}

	private ResourceOwnerPasswordResourceDetails resource() {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setClientId("000000");
		resource.setClientSecret("0000000");
		resource.setAccessTokenUri("http://localhost:8082/BasicMicroService/oauth/token");// Oauth2.0 服务端链接
		resource.setScope(Arrays.asList("read","write"));// 读写权限
		resource.setUsername("0000000");
		resource.setPassword("0000000");
		resource.setGrantType("password");// Oauth2.0 使用的模式 为密码模式
		return resource;
	}
	/**
	 * 登录验证，返回路由和令牌
	 */
	@PostMapping(value="/loginPost")
	public Map<String, Object> loginPost(@RequestBody AccountEntity accountEntity,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
		Map<String, Object> map = new HashMap<>();
		String account = accountEntity.getAccount();
		String password = accountEntity.getPassword();

		OAuth2RestOperations oauthRestTemplate = restTemplate();
		ResponseEntity<String> str = oauthRestTemplate.postForEntity("http://192.163.20.79:8082/BasicMicroService/log",accountEntity,String.class);
		System.out.println(str.hasBody());
		//模拟返回受保护资源
		String redirectUrl = "http://localhost:" + urlpro.getPort();
		/***************************************第1步通过用户名密码发起请求***************************************/
		ResponseEntity<String> response = new TestRestTemplate(account, password).postForEntity(
				"http://localhost:" + urlpro.getPort()+ "/BasicMicroService/oauth/authorize?response_type=code&client_id=normal-app&redirect_uri={redirectUrl}",
				null, String.class, redirectUrl);
		//认证失败
		if(response.getStatusCodeValue()==401){
			map.put("success", false);
			map.put("message", "（未授权） 请重新进行身份验证！");
			map.put("router", null);
			return map;
		}
		//获取cookie里面的JSSIONID cookie
		List<String> setCookie = response.getHeaders().get("Set-Cookie");
		String jSessionIdCookie = setCookie.get(0);
		String cookieValue = jSessionIdCookie.split(";")[0];
		//组织一个http请求头部,放入cookie
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", cookieValue);
		System.out.println("第1步执行完成，表示申请认证，获取jsessionId："+cookieValue);
		/***************************************第2步再次发起请求获取授权码***************************************/
		response = new TestRestTemplate().postForEntity(
				"http://localhost:" + urlpro.getPort()+ "/BasicMicroService/oauth/authorize?response_type=code&client_id=normal-app&redirect_uri={redirectUrl}&user_oauth_approval=true&authorize=Authorize",
				new HttpEntity<Object>(headers), String.class, redirectUrl);
		String location = response.getHeaders().get("Location").get(0);
		//获取url
		URI locationURI = new URI(location);
		//获取url后面的请求参数，即获取授权码code=xxx
		String query = locationURI.getQuery();
		System.out.println("第2步执行完成，获取授权码："+query);
		/***************************************第3步组织授权码获取令牌***************************************/
		location = "http://localhost:" + urlpro.getPort() + "/BasicMicroService/oauth/token?" + query
				+ "&grant_type=authorization_code&client_id=normal-app&redirect_uri={redirectUrl}";
		response = new TestRestTemplate("normal-app", "").postForEntity(location, new HttpEntity<>(new HttpHeaders()),
				String.class, redirectUrl);
		//获取access_token信息
		HashMap<?, ?> jwtMap = new ObjectMapper().readValue(response.getBody(), HashMap.class);
		String accessToken = (String) jwtMap.get("access_token");
		System.out.println("第3步执行完成，获取令牌："+accessToken);
		/***************************************第4步返回令牌***************************************/
		AccountEntity accountfinded=accountServer.findByAccount(account);
		Map<String,Object> routerMap = this.getRouterByAccount(accountfinded);//获得路由信息
		map.put("success", true);
		map.put("message", "登录成功");
		map.put("router", routerMap);
		map.put("accessToken", accessToken);
		System.out.println("第4步执行完成，返回令牌："+accessToken);
		return map;
	}

	/**
	 * 根据账号获得路由信息
	 * @author lilei
	 * @date   2017年12月11日 下午12:20:42 
	 * @param account
	 * @return
	 */
	public Map<String,Object> getRouterByAccount(AccountEntity account) {
		//返回路由信息
		Map<String,Object> routerMap = new HashMap<>();//拼接router
		String roleNum = account.getRolenum();//现在先默认只有一个角色
		//根据roleNum找到所有permission
		List<PermissionEntity> permissionList = permissionService.findByRolenum(roleNum);
		if(permissionList!=null&&permissionList.size()!=0){
			//根据num找到所有FunctionPointEntity

			Map<String, MenuEntity> menuMap = new HashMap<>();//菜单map
			Map<String, List<FunctionPointEntity>> functionPointMap = new HashMap<>();//功能点map

			for(PermissionEntity permission:permissionList){
				System.out.println("permissionId:"+permission.getId()+"---function:"+permission.getFunctionpointnum());
				FunctionPointEntity functionPoint = functionPointService.findFirstByNum(permission.getFunctionpointnum());
				List<FunctionPointEntity> flist = new ArrayList<>();
				System.out.println("functionPointId="+functionPoint.getId()+"-----menunum:"+functionPoint.getMenunum());
				if(null != functionPointMap.get(functionPoint.getMenunum())){//添加functionPoint到list
					flist = functionPointMap.get(functionPoint.getMenunum());
					flist.add(functionPoint);
				}else{//添加键值对
					flist.add(functionPoint);
				}
				functionPointMap.put(functionPoint.getMenunum(), flist);//功能点以menu为key放入map

				//根据菜单编号去查Menu，再放入以menenum为key的map中
				if(!functionPoint.getMenunum().isEmpty()){
					MenuEntity menuEntity = menuService.findFirstByMenunum(functionPoint.getMenunum());
					menuMap.put(menuEntity.getMenunum(), menuEntity);//把菜单放入map
				}else{
					//?
				}
			}
			//拼接json串
			List<Object> childrenList = new ArrayList<Object>();
			for (Entry<String, MenuEntity> entry : menuMap.entrySet()) {//循环menuMap，拼children
				Map<String,Object> childrenMap = new HashMap<>();
				MenuEntity menuEntity =  entry.getValue();
				String menuUrl = menuEntity.getMenuurl();

				//分割字符串。可以写成一个Utils
				String name = "";
				if(!menuUrl.isEmpty()){
					menuUrl=menuUrl.trim();
					int beginIndex = menuUrl.lastIndexOf("/");
					if(beginIndex!=-1){
						name = menuUrl.substring(beginIndex+1,menuUrl.length());
					}else{
						//没有斜杠，
						name = menuUrl;
					}
				}

				childrenMap.put("path", "/"+name);
				childrenMap.put("name", name);
				childrenMap.put("component", menuUrl);

				List<FunctionPointEntity> fmList =  functionPointMap.get(entry.getKey());
				List<Object> metaList = new ArrayList<>();
				if(fmList!=null){//拼meta
					for(FunctionPointEntity f : fmList){
						Map<String,String> fMap = new HashMap<>();
						fMap.put("name", f.getName());
						fMap.put("apiUrl", f.getUrl());
						metaList.add(fMap);
					}
				}
				childrenMap.put("meta", metaList);
				childrenList.add(childrenMap);
			}
			//拼routerMap
			routerMap.put("path", "/MainMenu");
			routerMap.put("name", "MainMenu");
			routerMap.put("component", "basicplugin/view/MainMenu");
			routerMap.put("children", childrenList);
		}
		return routerMap;
	}
}