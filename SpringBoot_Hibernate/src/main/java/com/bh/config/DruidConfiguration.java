package com.bh.config;

import java.sql.SQLException;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

@Component
@ConfigurationProperties(prefix="spring.datasource")
public class DruidConfiguration{

	private String type ;
	private String driverClassName ;
	private String url ;
	private String username ;
	private String password ;

	private int initialSize ;
	private int minIdle ;
	private int maxActive ;
	private int maxWait ;
	private int timeBetweenEvictionRunsMillis ;
	private int minEvictableIdleTimeMillis ;
	private String validationQuery ;
	private boolean testWhileIdle ;
	private boolean testOnBorrow ;
	private boolean testOnReturn ;
	private boolean poolPreparedStatements ;
	private int maxPoolPreparedStatementPerConnectionSize;
	private String filters ;
	private String connectionProperties ;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
 
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}
	public int getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}
	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}
	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}
	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}
	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}
	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}
	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	public boolean isTestOnReturn() {
		return testOnReturn;
	}
	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}
	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}
	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}
	public int getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}
	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}
	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}
	public String getConnectionProperties() {
		return connectionProperties;
	}
	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}
	@Bean
	public DruidDataSource dataSource() {

		DruidDataSource dataSource = new DruidDataSource();
		//dataSource.setDbType(type);
		System.out.println(type);
		dataSource.setDriverClassName(driverClassName);
		System.out.println(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setInitialSize(initialSize);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxActive(maxActive);
		dataSource.setMaxWait(maxWait);
		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setTestOnReturn(testOnReturn);
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setConnectionProperties(connectionProperties);
		
		try {
			//开启Druid的监控统计功能，mergeStat代替stat表示sql合并,wall表示防御SQL注入攻击
			dataSource.setFilters(filters);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataSource;
	}
	/**
	 * 注册一个Druid内置的StatViewServlet，用于展示Druid的统计信息。
	 * @return
	 */
	@Bean
	public ServletRegistrationBean DruidStatViewServlet(){
		//org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");

		//添加初始化参数：initParams

		//白名单 (没有配置或者为空，则允许所有访问)
		servletRegistrationBean.addInitParameter("allow","192.163.20.79,127.0.0.1");
		//IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
		servletRegistrationBean.addInitParameter("deny","192.168.1.80");
		//登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginUsername","root");
		servletRegistrationBean.addInitParameter("loginPassword","123456");
		//是否能够重置数据(禁用HTML页面上的“Reset All”功能)
		servletRegistrationBean.addInitParameter("resetEnable","false");
		return servletRegistrationBean;
	}
	/**
	 * 注册一个：filterRegistrationBean,添加请求过滤规则
	 * @return
	 */
	@Bean
	public FilterRegistrationBean druidStatFilter(){

		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

		//添加过滤规则.
		filterRegistrationBean.addUrlPatterns("/*");

		//添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter(
				"exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
		return filterRegistrationBean;
	}
	/**
	 * 监听Spring
	 *  1.定义拦截器
	 *  2.定义切入点
	 *  3.定义通知类
	 * @return
	 */
	@Bean
	public DruidStatInterceptor druidStatInterceptor(){
		return new DruidStatInterceptor();
	}
	@Bean
	public JdkRegexpMethodPointcut druidStatPointcut(){
		JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
		/* String patterns = "com.bh.*.*.service.*";
        String patterns2 = "com.bh.*.*.mapper.*";  
        druidStatPointcut.setPatterns(patterns,patterns2);*/
		return druidStatPointcut;
	}
	@Bean
	public Advisor druidStatAdvisor() {
		return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
	}
}