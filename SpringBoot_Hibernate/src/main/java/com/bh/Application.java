package com.bh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author bh
 *  /@SpringBootApplication申明让spring boot自动给程序进行必要的配置，
 * *等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan
 */
@ComponentScan(basePackages={"com.bh"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class })  
@SpringBootApplication
public class Application {

	/**main方法通过调用run，将业务委托给了Spring Boot的SpringApplication类。
	 * SpringApplication将引导我们的应用，启动Spring，相应地启动被自动配置的Tomcat web服务器
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	/**
     * 跨域过滤器 
     * @return
     */
    @Bean  
    public CorsFilter corsFilter() {  
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
        source.registerCorsConfiguration("/**",buildConfig());
        return new CorsFilter(source);  
    }
    
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();  
        corsConfiguration.addAllowedOrigin("*");  
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");  
        return corsConfiguration;
    }
}