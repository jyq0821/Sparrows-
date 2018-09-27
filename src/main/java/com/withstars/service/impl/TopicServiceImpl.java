package com.withstars.service.impl;

import com.withstars.dao.ReplyMapper;
import com.withstars.dao.TabMapper;
import com.withstars.dao.TopicMapper;
import com.withstars.dao.UserTopicMapper;
import com.withstars.domain.Tab;
import com.withstars.domain.Topic;
import com.withstars.exception.ServiceException;
import com.withstars.service.TopicService;
import com.withstars.vo.PageObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    public TopicMapper topicDao;
    @Autowired
    private ReplyMapper replyMapperDao;
    @Autowired
    private UserTopicMapper userTopicDao;
    
    @Autowired
	public TabMapper tabDao;

    @Override
    //获取全部主题
    public List<Topic> getAllTopics() {
        return topicDao.getAllTopics();
    }

    @Override
    //获取指定id主题
    public Topic selectById(Integer id) {
        Topic topic=topicDao.selectById(id);
        return topic;
    }

    @Override
    public List<Topic> listMostCommentsTopics() {
        return topicDao.listMostCommentsTopics();
    }

    @Override
    public boolean addTopic(Topic topic) {
        return topicDao.insert(topic)>0;
    }

    @Override
    public boolean clickAddOne(Integer id) {
        return topicDao.clickAddOne(id)>0;
    }

    @Override
    public int getTopicsNum() {
        return topicDao.getTopicsNum();
    }

    @Override
    public PageObject<Topic> listTopicsAndUsers(Integer tabId,Integer pageCurrent) {
		System.out.println("TopicServiceImpl.listTopicsAndUsers()"+tabId);
		//1.参数有效性验证(只验证pageCurrent)
		System.out.println("1111111111"+pageCurrent);
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("当前页面错误"); 
		//2.查询总记录数并进行验证
		int rowCount = topicDao.rowCount(tabId);
		if(rowCount==0)
			throw new IllegalArgumentException("没有记录");
		//3.查询帖子数据
		int pageSize=10;
		int startIndex=(pageCurrent-1)*pageSize;
		List<Topic> result = topicDao.findPageObjects(tabId, startIndex, pageSize);
		//4.对查询结果进行封装
		PageObject<Topic> po = new PageObject<Topic>();
		po.setRecords(result);
		po.setPageSize(pageSize);
		po.setRowCount(rowCount);
		po.setPageCurrent(pageCurrent);
		return po;
	}

    @Override
    public List<Topic> listTopicsAndUsersOfTab(Integer tabId) {
        return topicDao.listTopicsAndUsersOfTab(tabId);
    }
    
  //通过帖子id删除帖子
  	@Override
  	public int deleteByPrimaryKey(Integer id) {
  		//1.验证参数的有效性
  		if(id==null||id<0)throw new IllegalArgumentException("id不存在");
  		//2.执行dao操作
  		int rows=topicDao.deleteByPrimaryKey(id);
  		if(rows==0)throw new ServiceException("数据已经不存在");
  		int result = replyMapperDao.deletePrimaryKeyBytopicId(id);
  		//3.返回结果
  		return rows;
  	}
  
  	/**
  	 * 
  	 * @param record
  	 * @return
  	 */

  	@Override
  	public int updateByPrimaryKey(Topic record) {
  		//1.验证有效性
  		if(record==null)
  			throw new IllegalArgumentException("帖子信息不能为空");
  		if(record.getUserId()==null)throw new RuntimeException("用户不能为空");
  		//2.更新数据
  		int rows=0;
  		/*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		String CurTime = simpleDateFormat.format(new Date());*/
  		record.setUpdateTime(new Date());
  		rows=topicDao.updateByPrimaryKeySelective(record);
  		//3.返回结果
  		return rows;
  	}

	@Override
	public int deleteCollectTopic(Integer topicId) {
		// TODO Auto-generated method stub
		if(topicId==null||topicId<1){
			throw new ServiceException("参数传入有误");
		}
		int count = 
				userTopicDao.deleteCollectionById("topic_id", topicId);
		return count;	
	}
	@Override
	public List<String> findTopicIdsByTopicId(Integer userId) {
		List<String> list = new ArrayList<>();
		//1.查询多有板块 TabList
		List<Tab> allTabs = tabDao.getAllTabs();
		//2. 遍历TabList
		for (int i=0; i<allTabs.size(); i++) {
			Tab tab = allTabs.get(i);
			//2.1根据userId  tabId 查询 发帖的数量
			int count = topicDao.findTopicIdsByTopicId(userId, tab.getId());
			//2.2数据添加到map
			String str = allTabs.get(i).getTabName()+":"+count;
			list.add(str);
		}
		return list;
	}
}
