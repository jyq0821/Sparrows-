package com.withstars.service.impl;

import com.withstars.dao.ReplyMapper;
import com.withstars.domain.Reply;
import com.withstars.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    public ReplyMapper replyDao;

    @Override
    public List<Reply> getRepliesOfTopic(Integer topicId) {
        return replyDao.getRepliesOfTopic(topicId);
    }

    @Override
    public boolean addReply(Reply reply) {
        return replyDao.insert(reply)>0;
    }

    @Override
    public int repliesNum(Integer topicId) {
        return replyDao.getRepliesNum(topicId);
    }
}
