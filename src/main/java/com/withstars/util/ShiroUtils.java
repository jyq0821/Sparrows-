package com.withstars.util;

import org.apache.shiro.SecurityUtils;

import com.withstars.domain.User;





public class ShiroUtils {
	
	public static User getPrincipal(){
		 return (User)SecurityUtils
		.getSubject().getPrincipal();
	 }

}
