package com.bh.configure;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
public class OAuthSecurityConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private AuthenticationManager authenticationManager;


	@Bean
	public TokenStore tokenStore() {
		return  new JwtTokenStore(accessTokenConverter());
	}

	/**创建令牌
	 * @return
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
			/***
			 * 重写增强token方法,用于自定义一些token返回的信息
			 */
			@Override
			public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
				String userName = authentication.getUserAuthentication().getName();
				User user = (User) authentication.getUserAuthentication().getPrincipal();// 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
				/** 自定义一些token属性 ***/
				final Map<String, Object> additionalInformation = new HashMap<>();
				additionalInformation.put("userName", userName);
				additionalInformation.put("roles", user.getAuthorities());
				((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
				OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
				return enhancedToken;
			}
		};
		accessTokenConverter.setSigningKey("123");// 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
		return accessTokenConverter;
	}


	@Value("${resource.id:spring-boot-application}") // 默认值spring-boot-application
	private String resourceId="spring-boot-application";

	@Value("${access_token.validity_period:3600}") // 默认值3600
	int accessTokenValiditySeconds = 3600;
	
	/**客户端详情信息
	 * */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient("normal-app")
		.authorizedGrantTypes("authorization_code", "implicit")
		.authorities("ROLE_CLIENT")
		.scopes("read", "write")
		.resourceIds(resourceId)
		.accessTokenValiditySeconds(accessTokenValiditySeconds)
		.and()
		.withClient("trusted-app")
		.authorizedGrantTypes("client_credentials", "password")
		.authorities("ROLE_TRUSTED_CLIENT")
		.scopes("read", "write")
		.resourceIds(resourceId)
		.accessTokenValiditySeconds(accessTokenValiditySeconds)
		.secret("secret");
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		//oauthServer.checkTokenAccess("isAuthenticated()");
		oauthServer.checkTokenAccess("permitAll()");
		oauthServer.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(this.authenticationManager);
		endpoints.accessTokenConverter(accessTokenConverter());
		endpoints.tokenStore(tokenStore());
	}	
}