package com.withstars.aop;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.withstars.anno.RequestLog;
import com.withstars.dao.LoginLogMapper;
import com.withstars.domain.Logs;
import com.withstars.util.DeviceUtils;
import com.withstars.util.IPUtils;
import com.withstars.util.ShiroUtils;



/**
 * 
 * @author lt
 *
 */
@Component
@Aspect
public class LogAspect {
     private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private LoginLogMapper loginLogMapper;
    @Before("execution(* com.withstars.controller.UserController.signin(..))")
    public void loginLogAspect(JoinPoint joinPoint){
        String methodName=joinPoint.getSignature().toShortString();
        String args=joinPoint.getArgs().toString();
        log.info("---Before method "+methodName+" invoke, param:" +args+"---");
    }
    @Pointcut("@annotation(com.withstars.anno.RequestLog)")
    public void logPointCut(){}
    @Around("logPointCut()")
    public  Object  aroundLogin（ProceedingJoinPoint  // a啊啊啊
    		jpt) throws Throwable{
    	long startTime=System.currentTimeMillis();
    	Object result=jpt.proceed();
    	long endTime=System.currentTimeMillis();
    	long totalTime=endTime-startTime;
    	log.info("方法执行的总时长为:"+totalTime);
    	saveSysLog(jpt,totalTime);
    	return result;
    }
	private void saveSysLog(ProceedingJoinPoint point, long totalTime) throws Throwable, SecurityException {
		// TODO Auto-generated method stub
		//1.获取日志信息
    	MethodSignature ms=
    	(MethodSignature)point.getSignature();
    	Class<?> targetClass=
    	point.getTarget().getClass();
    	String className=targetClass.getName();
    	//获取接口声明的方法
    	String methodName=ms.getMethod().getName();
    	Class<?>[] parameterTypes=ms.getMethod().getParameterTypes();
    	//获取目标对象方法
    	Method targetMethod=targetClass.getDeclaredMethod(
    			    methodName,parameterTypes);
    	//判定目标方法上是否有RequestLog注解
    	boolean flag=
    	targetMethod.isAnnotationPresent(RequestLog.class);
    	String username=ShiroUtils.getPrincipal().getUsername();
    	Integer userid = ShiroUtils.getPrincipal().getId();
    	Logs log=new Logs();
    	if(flag){
    	RequestLog requestLog=
    	targetMethod.getDeclaredAnnotation(RequestLog.class);
    	log.setOperation(requestLog.value());
    	}
    	log.setUserId(userid);
    	log.setUserName(username);
    	//className.methodName()
    	log.setMethod(className+"."+methodName);
    	//ip 地址
    	log.setIp(IPUtils.getIpAddr());
    	log.setTime(totalTime);
    	//浏览器设备信息
    	log.setDevice(DeviceUtils.getBrowserName());
    	//3.保存日志信息
    	loginLogMapper.insertLogs(log);
	}
}
