
package com.withstars.util;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import eu.bitwalker.useragentutils.UserAgent;




/**
 * 仿照IPutils
 * @author lt
 *
 */
public class DeviceUtils {
	private static Logger logger = LoggerFactory.getLogger(DeviceUtils.class);
	/**
	 * 获取浏览器设备名称
	 * 使用Nginx等反向代理软件， 则不能通过request.getHeader("User-Agent")
	 * 
	 */
	public static String getBrowserName() {
		HttpServletRequest request=
		((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		String browserName= userAgent.getBrowser().toString();
		
        return browserName;
    }
	
}
