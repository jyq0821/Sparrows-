package com.withstars.dao;

import com.withstars.domain.LoginLog;
import com.withstars.domain.Logs;

public interface LoginLogMapper {
	/**
	 * 通过主键删除
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Long id);

    /**
     * 写入登录日志（已弃用）
     * @param record
     * @return
     */
    int insert(LoginLog record);

    /**
     * 
     * @param record
     * @return
     */
    int insertSelective(LoginLog record);

    LoginLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoginLog record);

    int updateByPrimaryKey(LoginLog record);
    
    int insertLogs(Logs log);
}