package com.bh.common;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**全局异常捕捉
 * @author bh
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler{

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(HttpServletRequest req, Exception e) {

		//打印异常信息：
		e.printStackTrace();
		System.out.println("GlobalExceptionHandler.defaultErrorHandler()");
		return "exception";
	}
}

