package com.bh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bh.entity.AccountEntity;
import com.bh.entity.AccountServer;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//决定Spring Security的前注解是否可用 
@EnableWebSecurity //通过@EnableWebSecurity注解开启Spring Security的功能
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private AccountServer accountServer;
	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication()
		// .withUser("user").password("password").roles("USER")
		// .and()
		// .withUser("app_client").password("nopass").roles("USER")
		// .and()
		// .withUser("admin").password("password").roles("ADMIN");
		//配置用户来源于数据库
		auth.userDetailsService(userDetailsService());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/account/loginPost").permitAll().anyRequest().authenticated()
		.and().httpBasic().and().csrf().disable();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
			// 通过用户名获取用户信息
			 AccountEntity accountEntity=accountServer.findByAccount(name);
			// 创建spring security安全用户
			if(name.equals(accountEntity.getAccount())){
				User user = new User(accountEntity.getAccount(), accountEntity.getPassword(),
					AuthorityUtils.createAuthorityList(accountEntity.getRolenum()));
				return user;
			}else{
				throw new UsernameNotFoundException("用户不存在！");
			}
		  }
		};
	}
}
