package com.withstars.controller;

import com.withstars.anno.RequestLog;
import com.withstars.domain.LoginLog;
import com.withstars.domain.Tab;
import com.withstars.domain.Topic;
import com.withstars.domain.User;
import com.withstars.exception.ServiceException;
import com.withstars.service.LoginLogService;
import com.withstars.service.TopicService;
import com.withstars.service.UserService;
import com.withstars.service.impl.LoginLogServiceImpl;
import com.withstars.service.impl.TopicServiceImpl;
import com.withstars.service.impl.UserServiceImpl;
import com.withstars.util.ProduceMD5;
import com.withstars.vo.JsonResult;

import eu.bitwalker.useragentutils.UserAgent;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * 用户相关控制类
 */
@Controller
public class UserController {

	@Autowired
	public UserService userService;

	@Autowired
	public LoginLogService loginLogService;

	@Autowired
	public TopicService topicService;

	/**
	 * 用户注册
	 */
	@RequestMapping("/user/add/do")
	@ResponseBody
	//@RequestLog("注册操作")
	public JsonResult addUser(User user) {

		System.out.println(user.toString());
		// 用户类型
		Byte type = new Byte("0");
		// 密码加密处理
		String password = ProduceMD5.getMD5(user.getPassword());
		// 生成随机数，用于生成头像URL
		Random rand = new Random();
		int randomNum = rand.nextInt(10) + 1;
		String avatarUrl = "/img/avatar/avatar-default-" + randomNum + ".png";
		user.setPassword(password);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setCredit(0);
		user.setType(type);
		user.setAvatar(avatarUrl);
		boolean ifSucc = userService.addUser(user);
		System.out.print(ifSucc);
		return new JsonResult("signup ok");
	}

	/**
	 * 用户登陆
	 * 
	 * @param request
	 * @param session
	 * @return 0:用户名不存在 1:密码错误 2:登录成功
	 */

	@RequestMapping("/api/loginCheck")
	@ResponseBody
	public Object signin(HttpServletRequest request, HttpSession session) {
		// 处理参数
		String password = ProduceMD5.getMD5(request.getParameter("password"));
		String username = request.getParameter("username");
		// 验证用户名密码
		int loginVerify = userService.login(username, password);

		HashMap<String, String> res = new HashMap<String, String>();

		// 登录成功
		if (loginVerify == 2) {
			User user = userService.getUserByUsername(username);
			Integer userId = user.getId();
			// 添加积分
			boolean ifSuccAddCredit = userService.addCredit(1, userId);
			// 用户信息写入session
			session.setAttribute("userId", userId);
			session.setAttribute("username", username);
			// 获取登录信息
			String ip = getRemortIP(request);
			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
			// 获取用户的浏览器名
			String userbrowser = userAgent.getBrowser().toString();
			// 写入登录日志
			LoginLog log = new LoginLog();
			log.setDevice(userbrowser);
			log.setIp(ip);
			log.setUserId(userId);
			log.setLoginTime(new Date());
			boolean ifSuccAddLog = loginLogService.addLog(log);

			res.put("stateCode", "2");
		}
		// 密码错误
		else if (loginVerify == 1) {
			res.put("stateCode", "1");
		}
		// 用户名不存在
		else {
			res.put("stateCode", "0");
		}
		return res;
	}

	@RequestMapping("/api/loginCheck2")
	@ResponseBody
	@RequestLog("登录操作")
	public Object signin2(String username, String password, HttpSession session) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();

		subject.login(token);
		User user = userService.getUserByUsername(username);
		Integer userId = user.getId();
		session.setAttribute("userId", userId);
		session.setAttribute("username", username);

		return new JsonResult("login ok");
	}
	/**
	 * 用户登出
	 */
	/*
	 * @RequestMapping("/signout") //HttpSession session public String
	 * signout(){ System.out.println("######");
	 * //session.removeAttribute("userId");
	 * //session.removeAttribute("username"); return "redirect:/index"; }
	 */

	/**
	 * 获取客户端IP
	 */
	public String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	/**
	 * 用户个人主页
	 */
	@RequestMapping("/member/{username}")
	public ModelAndView personalCenter(@PathVariable("username") String username, HttpSession session) {
		boolean ifExistUser = userService.existUsername(username);
		// 获取统计信息
		int topicsNum = topicService.getTopicsNum();
		int usersNum = userService.getUserCount();

		// 获取用户信息
		Integer uid = (Integer) session.getAttribute("userId");
		User user = userService.getUserById(uid);
		
	
		// 最热主题
		List<Topic> hotestTopics = topicService.listMostCommentsTopics();
		
		
		List<String> tipicCount = topicService.findTopicIdsByTopicId(uid);
		
		

		ModelAndView mv = new ModelAndView("user_info");
		mv.addObject("hotestTopics", hotestTopics);
		if (ifExistUser) {
			User resultUser = userService.getUserByUsername(username);
			mv.addObject("userInfo", resultUser);
			mv.addObject("topicsNum", topicsNum);
			mv.addObject("usersNum", usersNum);
			mv.addObject("user", user);
			mv.addObject("tipicCount", tipicCount);
			return mv;
		} else {
			String errorInfo = new String("会员未找到");
			mv.addObject("errorInfo", errorInfo);
			return mv;
		}
	}

	@RequestMapping("/settings")
	public ModelAndView settings(HttpServletRequest request, HttpSession session) {

		Integer uid = (Integer) session.getAttribute("userId");
		User user = userService.getUserById(uid);

		// 最热主题
		List<Topic> hotestTopics = topicService.listMostCommentsTopics();

		ModelAndView mv = new ModelAndView("settings");
		mv.addObject("user", user);
		mv.addObject("hotestTopics", hotestTopics);
		return mv;
	}

	/*@RequestMapping(value = "/settings/avatar", method = RequestMethod.GET)
	public ModelAndView updateAvatar(HttpServletRequest request, HttpSession session) {

		Integer uid = (Integer) session.getAttribute("userId");
		User user = userService.getUserById(uid);

		// 最热主题
		List<Topic> hotestTopics = topicService.listMostCommentsTopics();

		ModelAndView mv = new ModelAndView("update_avatar");
		mv.addObject("user", user);
		mv.addObject("hotestTopics", hotestTopics);
		return mv;
	}*/

	@RequestMapping(value = "/settings/avatar/update", method = RequestMethod.POST)
	public String updateAvatarDo(@RequestPart("avatar") MultipartFile avatarFile, HttpServletRequest request,
			HttpSession session) {
		Integer uid = (Integer) session.getAttribute("userId");

		String fileName = avatarFile.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		Long date = System.currentTimeMillis();
		String newFileName = date + "-" + uid + "." + suffix;
		String absolutePath = session.getServletContext().getRealPath("/static/img/avatar") + "/" + newFileName;
		String relativePath = "/img/avatar" + "/" + newFileName;
		User newUser = new User();
		newUser.setAvatar(relativePath);
		newUser.setId(uid);
		File file = new File(absolutePath);

		if (!file.exists()) {
			try {
				avatarFile.transferTo(file);
				userService.updateUser(newUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*User user = userService.getUserById(uid);

		// 最热主题
		List<Topic> hotestTopics = topicService.listMostCommentsTopics();

		ModelAndView mv = new ModelAndView("update_avatar");
		mv.addObject("user", user);

		mv.addObject("hotestTopics", hotestTopics);
		return mv;*/
		
		return "redirect:/settings";
	}
	/**
	 * 帖子收藏操作
	 * 
	 */
	
	@RequestMapping("doCollectTopic/{topicId}")
    public ModelAndView doCollectTopic(@PathVariable("topicId") Integer topicId,
    		HttpServletRequest request,
			HttpSession session) {
		
		ModelAndView mv = new ModelAndView("redirect:/t/"+topicId);
		Integer uid = (Integer) session.getAttribute("userId");
		System.out.println(topicId);
	    System.out.println(uid);
	    try {
	    	int count = userService.collectTopic(uid, topicId);
	    	Integer message=0;
	    	if(count==1){
	    		message=1;
	    	}
		    mv.addObject("message", message);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    System.out.println("==========================");
        return mv;
    }
	@RequestMapping("doDeleteCollectTopic/{topicId}")
    public ModelAndView doDeleteCollectTopic(@PathVariable("topicId") Integer topicId,
    		HttpServletRequest request,
			HttpSession session) {
		
		ModelAndView mv = new ModelAndView("redirect:/t/"+topicId);
		Integer uid = (Integer) session.getAttribute("userId");
		System.out.println(topicId);
	    System.out.println(uid);
	    try {
	    	int count = userService.deleteCollectTopic(uid);
	    	Integer message=0;
	    	if(count==1){
	    		message=2;
	    	}
		    mv.addObject("message", message);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    System.out.println("==========================");
        return mv;
    }
    /**
     * 根据用户id查询用户的所有信息
     * @param lxj
     * @return
     */
    @RequestMapping(value = "/user/information",method = RequestMethod.POST)
    public User sqlUserInformation(Integer id){
    	
    	User user = userService.getUserById(id);
		return user;
    	
    }
    
    /**
     * 获取用户名在数据库中查找相应用户的数据信息
     * 根据用户的id更新信息
     * 需要一个username参数
     * @author Administrator lxj
     * @return
     */
    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    
    public ModelAndView userUpdateByUserId(User user){
    	System.out.println("控制层"+user);
    	Integer updateResult=0;
    	if (userService.updateUserByUserId(user)){
    		updateResult=1;
    	}else{
    		updateResult=2;
    	}
    	ModelAndView mv=new ModelAndView("settings");
    	mv.addObject("updateResult", updateResult);
    	
    	return mv;
		
	
    }
    
    /**
     * @author lxj
     * 根据用户的用户名查找用户id并输入密码
     */
    @RequestMapping(value = "/user/updatePassward",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult userUpdatePassword(String oldPassword, String newPassword, Integer id,String verifyPassword){
    	System.out.println(oldPassword);
    	System.out.println(newPassword);
    	System.out.println(id);
    	System.out.println(verifyPassword);
    	if (!newPassword.equals(verifyPassword)) {
			throw new ServiceException("新密码和确认密码要一致哦亲");
		}
    	Integer updatePasswordResult=0;
    	
    	if (userService.updatePassword(oldPassword, newPassword,id)){
        		updatePasswordResult=1;
       }else{
        		updatePasswordResult=2;
        }
		return new JsonResult("修改成功");
    }
    /**
     * 注册名称检验（不可以重名）
     * 
     */
    @RequestMapping("/user/checkUserName")
    @ResponseBody
    public JsonResult checkUserName(String username){
    	
    	User user = userService.getUserByUsername(username);
    	if(user!=null){
    		throw new ServiceException("用户名重复");
    	}else{
    		
    	}
		return new JsonResult("该用户名通过验证");
    }
}
