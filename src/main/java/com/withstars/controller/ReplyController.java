package com.withstars.controller;

import com.withstars.domain.Reply;
import com.withstars.service.impl.ReplyServiceImpl;
import com.withstars.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 回复相关控制类
 */
@Controller
public class ReplyController {

	@Autowired
	public ReplyServiceImpl replyService;
	@Autowired
	public UserServiceImpl userService;

	/**
	 * @lt 增加用户登录的判断，没有登录则点击评论后跳转到登录界面
	 * 添加评论
	 */
	@RequestMapping(value = "/reply/add", method = RequestMethod.POST)
	public ModelAndView addReply(HttpServletRequest request, HttpSession session) {
		// 处理参数
		ModelAndView view=null;
		Integer topicId = Integer.parseInt(request.getParameter("topicId"));
		/*if(session.getAttribute("userId")==null){
			view=new ModelAndView("redirect:/signin");
            return  view;
        }*/
		Integer replyUserId = Integer.parseInt(request.getParameter("replyUserId"));
		String content = request.getParameter("content");
		// 创建reply
		Reply reply = new Reply();
		reply.setTopicId(topicId);
		reply.setReplyUserId(replyUserId);
		reply.setContent(content);
		reply.setCreateTime(new Date());
		reply.setUpdateTime(new Date());
		// 执行添加
		try {
			boolean ifSucc = replyService.addReply(reply);
			// 添加积分
			boolean ifSuccAddCredit = userService.addCredit(1, replyUserId);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		// 新建视图
		view = new ModelAndView("redirect:/t/" + topicId);
		return view;
	}
}
