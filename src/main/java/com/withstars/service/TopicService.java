package com.withstars.service;

import com.withstars.domain.Topic;
import com.withstars.vo.PageObject;

import java.util.List;
import java.util.Map;

public interface TopicService {

	/**
	 * 获取全部主题
	 */
	List<Topic> getAllTopics();

	/**
	 * 获取全部主题及用户信息 用于渲染首页
	 */
     //List<Topic> listTopicsAndUsers();
    PageObject<Topic> listTopicsAndUsers(Integer tabId,Integer pageCurrent);

	/**
	 * 获取最多评论主题列表
	 * 
	 * @return
	 */
	List<Topic> listMostCommentsTopics();

	/**
	 * 获取全部主题及用户信息 用于渲染板块页面
	 */
	List<Topic> listTopicsAndUsersOfTab(Integer tabId);

	/**
	 * 获取指定ID主题
	 */
	Topic selectById(Integer id);

	/**
	 * 新建主题
	 */
	boolean addTopic(Topic topic);

	/**
	 * 点击量加一
	 */
	boolean clickAddOne(Integer id);

	/**
	 * 获取主题总数
	 */
	int getTopicsNum();

	/**
	 * 通过帖子id 删除帖子
	 * 
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 过主键id查找帖子信息
	 * 
	 * @param id
	 * @return
	 */
	int updateByPrimaryKey(Topic record);

	/**
	 * 删除帖子，删除收藏关系表
	 */
	int deleteCollectTopic(Integer topicId);
    /**
     * 获取用户板块和数量
     */
    List<String> findTopicIdsByTopicId(Integer userId);
}
