package com.bh.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
/**
 * 凡是被Spring管理的类，实现接口EnvironmentAware 重写方法 setEnvironment 可以在工程启动时，
 * 获取到系统环境变量和application配置文件中的变量。 
 */
@Configuration
public class MyEnvironmentAware implements EnvironmentAware{

	//注入application.properties的属性到指定变量中.
	@Value("${spring.datasource.url}")
	private String myUrl;

	/**
	 *注意重写的方法 setEnvironment是在系统启动的时候被执行。
	 */
	@Override
	public void setEnvironment(Environment environment) {

		//打印注入的属性信息.
		System.out.println("myUrl="+myUrl);

		//通过 environment获取到系统属性.
		System.out.println(environment.getProperty("JAVA_HOME"));

		//通过 environment同样能获取到application.properties配置的属性.
		System.out.println(environment.getProperty("spring.datasource.url"));

		//获取到前缀是"spring.datasource."的属性列表值.
		RelaxedPropertyResolver relaxedPropertyResolver = new RelaxedPropertyResolver(environment,"spring.datasource.");
		System.out.println("spring.datasource.url="+relaxedPropertyResolver.getProperty("url"));
		System.out.println("spring.datasource.driverClassName="+relaxedPropertyResolver.getProperty("driverClassName"));

	}
}