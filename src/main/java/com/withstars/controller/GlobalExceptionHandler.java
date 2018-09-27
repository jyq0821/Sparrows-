package com.withstars.controller;
import java.util.logging.Logger;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.withstars.vo.JsonResult;



/**
 * SpringMVC全局异常处理类
 *
 * @author Administrator
 * @time 2018/9/14 9:14
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger log=Logger.getLogger(GlobalExceptionHandler.class.getName());
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public JsonResult doHandlerRunTimeException(RuntimeException e) {
		return new JsonResult(e);
	}
	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public JsonResult doShiroException(ShiroException e) {
		 JsonResult jsonResult = new JsonResult();
		 jsonResult.setState(0);
		 if(e instanceof IncorrectCredentialsException){
			 jsonResult.setMessage("密码不正确");
		 }else if(e instanceof UnknownAccountException){
			 jsonResult.setMessage("用户不存在");
		 }else if(e instanceof LockedAccountException){
			 jsonResult.setMessage("用户禁用"); 
		 }else if(e instanceof AuthorizationException){
			 jsonResult.setMessage("您没有该权限");
		 } 
		 else{
			 e.printStackTrace();
		 }
		 return jsonResult;
	}
}
