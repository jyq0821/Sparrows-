package com.withstars.service;

import com.withstars.domain.User;

public interface UserService {

    /**
     * 用户注册
     */
    public boolean addUser(User user);

    /**
     * 登录验证
     */
    public int login(String username,String password);

    /**
     * 添加积分
     */
    public boolean addCredit(Integer points,Integer id);

    /**
     * 检查username是否存在
     * @param username
     * @return
     */
    public boolean existUsername(String username);

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    public User getUserByUsername(String username);


    /**
     * 获取用户信息
     * @param id
     * @return
     */
    public User getUserById(Integer id);

    /**
     * 获取用户数
     */
    public int getUserCount();

    public boolean updateUser(User user);
    
   /**
    * 
    * @param userId
    * @param topicId
    * @return
    */
    public int collectTopic(Integer userId,Integer topicId);
    
    /**
     * 
     * @param fieldName
     * @param userId
     * @return
     */
    public int deleteCollectTopic(Integer userId);
    
    public int checkCollectTopic(Integer userId,Integer topicId);
    
    /**
	 * 验证旧密码是否输入正确，若正确则执行修改
	 */
	public boolean updatePassword(String oldPassword,String newPassword,Integer id);
	
	
	/**
	 * @author lsj
	 * 通过username更新用户的个人信息接口
	 */
	
	public boolean updateUserByUserId(User user);
}
