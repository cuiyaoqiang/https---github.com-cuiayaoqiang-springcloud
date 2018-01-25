package com.bh.configure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bh.entity.Account;

@Configuration
public class OAuthWebConfig extends WebSecurityConfigurerAdapter {

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
		/*usernameParameter("username")
         .passwordParameter("password")*/
		http.authorizeRequests().antMatchers(/*HttpMethod.OPTIONS,*/"/login").permitAll().anyRequest().authenticated()
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
				Account account =new Account();
				account.setName("zs");
				account.setPassword("zs");
				account.setRoles(new String []{"ROLE_USER"});
				// 创建spring security安全用户
				if(name.equals(account.getName())){
					User user = new User(account.getName(), account.getPassword(),
							AuthorityUtils.createAuthorityList(account.getRoles()));
					return user;
				}else{
					throw new UsernameNotFoundException("user not found");
				}
			}
		};
	}
}