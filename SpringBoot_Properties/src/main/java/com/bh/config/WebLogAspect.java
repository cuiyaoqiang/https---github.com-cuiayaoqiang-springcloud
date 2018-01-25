package com.bh.config;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**spring.aop.auto属性默认是开启的，也就是说只要引入了AOP依赖后，默认已经增加了@EnableAspectJAutoProxy。
 *实现Web层的日志切面
 */
@Aspect
@Component
public class WebLogAspect {
	private Logger logger =  LoggerFactory.getLogger(this.getClass());

	ThreadLocal<Long>startTime =new ThreadLocal<Long>();

	/**
	 *定义一个切入点.
	 *解释下：
	 *
	 * ~第一个 * 代表任意修饰符及任意返回值.
	 * ~第二个 * 任意包名
	 * ~第三个 * 代表任意方法.
	 * ~第四个 * 定义在resource包或者子包
	 * ~第五个 * 任意方法
	 * ~ ..匹配任意数量的参数.
	 */
	@Pointcut("execution(public * com.bh.resource..*.*(..))")
	public void webLog(){
		
	}
	
	 
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint){
		
		startTime.set(System.currentTimeMillis());

		// 接收到请求，记录请求内容
		logger.info("doBefore()。。。 使用@Before在切入点开始处切入内容");
		ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容
		logger.info("URL : " + request.getRequestURL().toString());
		logger.info("HTTP_METHOD : " + request.getMethod());
		logger.info("IP : " + request.getRemoteAddr());
		logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName()+ "." + joinPoint.getSignature().getName());
		logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
		//获取所有参数方法一：
		Enumeration<String> enu=request.getParameterNames(); 
		while(enu.hasMoreElements()){ 
			String paraName=(String)enu.nextElement(); 
			System.out.println(paraName+": "+request.getParameter(paraName)); 
		} 
	}
	@After("webLog()")
	public void doAfter(){
		
		logger.info("doAfter()... 使用@After在切入点结尾处切入内容");
	}
	@AfterReturning("webLog()")
	public void doAfterReturning(JoinPoint joinPoint){
		
		logger.info("doAfterReturning()...使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）");
		logger.info("耗时（毫秒） : " + (System.currentTimeMillis()-startTime.get()));

	}
	/*@Around("webLog()")
	public void doAround(){
		
		logger.info("doAround()...+使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容");
	}*/
	@AfterThrowing("webLog()")
	public void doAfterThrowing(){
		
		logger.info("doAfterThrowing()...使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑。");
	}
}

