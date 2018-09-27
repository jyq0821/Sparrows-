package com.withstars.service.impl;

import com.withstars.dao.UserMapper;
import com.withstars.dao.UserTopicMapper;
import com.withstars.domain.User;
import com.withstars.exception.ServiceException;
import com.withstars.service.UserService;
import com.withstars.util.ProduceMD5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userDao;
    
    @Autowired 
    private UserTopicMapper userTopicDao;

    //用户注册
    @Override
    public boolean addUser(User user) {
        return userDao.addUser(user)>0;
    }

    //登录验证 0:用户名不存在 1:密码错误 2:验证成功
    @Override
    public int login(String username,String password) {
        //判断username是否存在
        boolean existUsername=existUsername(username);
        //若username存在，验证密码
        if (existUsername){
            User resUser=userDao.selectByUsername(username);
            if (resUser.getPassword().equals(password)){
                return 2;
            }
            return 1;
        }
        return 0;
    }

    //登陆后获取用户信息
    @Override
    public User getUserByUsername(String username){
        User resUser=userDao.selectByUsername(username);
        return resUser;
    }

    //增加积分
    @Override
    public boolean addCredit(Integer points,Integer id) {
        return userDao.addCredit(points,id)>0;
    }

    //username是否存在
    @Override
    public boolean existUsername(String username) {
        return userDao.existUsername(username)==1;
    }

    @Override
    public int getUserCount() {
        return userDao.getUserCount();
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateByPrimaryKeySelective(user)>0;
    }

	@Override
	public int collectTopic(Integer userId, Integer topicId) {
		
		if(userId==null||userId<1||topicId==null||topicId<1){
			throw new ServiceException("参数传入有误");
		}
		int oldRecord = userTopicDao.exactQuery(userId, topicId);
		if(oldRecord!=0){
			throw new ServiceException("已经收藏");
		}
		int count = userTopicDao.insertCollection(userId, topicId);
		
		return count;
	}

	@Override
	public int deleteCollectTopic(Integer userId) {
		if(userId==null||userId<1){
			throw new ServiceException("参数传入有误");
		}
		
		int count = 
				userTopicDao.deleteCollectionById("user_id", userId);
		return 0;
	}

	@Override
	public int checkCollectTopic(Integer userId, Integer topicId) {
		// TODO Auto-generated method stub
		int oldRecord = userTopicDao.exactQuery(userId, topicId);
		
		return oldRecord;
	}
	 /**
     * @author lxj
     * 根据传入的用户名称
     * 修改个人的用户信息
     */
    public boolean updateUserByUserId(User user) {
    	//通过传入的username在数据库中查找相应的User对象
    	String re="^[\\w-]+@[\\w-]+(\\.[\\w-]+)+$";
    	User olduser=userDao.selectByPrimaryKey(user.getId());
    	if (user.getEmail()==null||user.getEmail()=="") {
			throw new ServiceException("邮箱不能为空");
		}else if(!user.getEmail().matches(re)){
			throw new ServiceException("邮箱格式不正确");
		}else if (user.getPhoneNum()==null||user.getPhoneNum()=="") {
			throw new ServiceException("电话号码不能为空");
		}else if (user.equals(olduser)) {
			throw new ServiceException("参数未修改");
		}
    	//返回更新的结果
    	return userDao.updateByUserId(user);
    }
    
    
    
    /**
     * @author 修改用户密码
     */
    public boolean updatePassword(String oldPassword, String newPassword,Integer id)  {
    	//通过id查询用户的信息
    	User user = userDao.selectByPrimaryKey(id);
    	ProduceMD5 MD5 = new ProduceMD5();
    	oldPassword=MD5.getMD5(oldPassword);
    	System.out.println("old"+oldPassword);
    	System.out.println("oldxxx"+user.getPassword());
    	if (!oldPassword.equals(user.getPassword())) {
    		throw new ServiceException("旧密码输入有误");
		}
    	newPassword = MD5.getMD5(newPassword);
    	return userDao.updatePasswordById(newPassword, id);
    }
}
