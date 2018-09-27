package com.withstars.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 用于用户帖子收藏关系的维护
 * @author lt
 *
 */
public interface UserTopicMapper {

	int insertCollection(
			@Param("userId") Integer userId,
			@Param("topicId")Integer topicId);
	int deleteCollectionById(
			@Param("fieldName")  String  fieldName,
			@Param("Id") Integer Id);
	
	int exactQuery(
			@Param("userId") Integer userId,
			@Param("topicId")Integer topicId);
	
}
