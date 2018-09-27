package com.withstars.dao;

import com.withstars.domain.Reply;

import java.util.List;

public interface ReplyMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Reply record);

	int insertSelective(Reply record);

	Reply selectByPrimaryKey(Long id);

	List<Reply> getRepliesOfTopic(Integer topicId);

	int updateByPrimaryKeySelective(Reply record);

	int updateByPrimaryKeyWithBLOBs(Reply record);

	int updateByPrimaryKey(Reply record);

	int getRepliesNum(Integer topicId);

	// 通过帖子id删除回复
	int deletePrimaryKeyBytopicId(Integer topicId);
	
	//根据主题ID查询评论总记录数
    int findCountByTopicId (Integer topicId);

}