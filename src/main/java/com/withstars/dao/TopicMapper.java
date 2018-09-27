package com.withstars.dao;

import com.withstars.domain.Topic;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TopicMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Topic record);

	int insertSelective(Topic record);

	Topic selectById(Integer id);

	List<Topic> listTopicsAndUsers();

	List<Topic> listTopicsAndUsersOfTab(Integer tabId);

	List<Topic> listMostCommentsTopics();

	int updateByPrimaryKeySelective(Topic record);

	int updateByPrimaryKeyWithBLOBs(Topic record);

	int updateByPrimaryKey(Topic record);

	List<Topic> getAllTopics();

	int clickAddOne(Integer id);

	// 获取主题总数
	int getTopicsNum();

	// 根据类别分页查询主题
	List<Topic> findPageObjects(
			@Param("tabId") Integer tabId, 
			@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);

	int rowCount(@Param("tabId") Integer tabId);// 总记录数
	
	//基于角色id查询topic数量的方法
	int findTopicIdsByTopicId(
			@Param("userId") Integer userId,
			@Param("tabId") Integer tabId);
	
	List<Topic> findTopicsByKeyWord(@Param("") String keyWord,
									@Param("tabId") Integer tabId);
	
}