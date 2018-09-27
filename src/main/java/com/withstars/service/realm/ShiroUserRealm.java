package com.withstars.service.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withstars.dao.UserMapper;
import com.withstars.domain.User;



/**
 * 
 * 完成用户认证信息授权信息的获取封装数据。
 * 
 * @author lt
 *
 */
@Service
public class ShiroUserRealm extends AuthorizingRealm {


	@Autowired
	private UserMapper userDao;
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		// TODO Auto-generated method stub
		HashedCredentialsMatcher cMatcher = new HashedCredentialsMatcher();
		cMatcher.setHashAlgorithmName("MD5");
		super.setCredentialsMatcher(cMatcher);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {	
		return null;
	}
	/**
	 * 认证;
	 * 
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		System.out.println("取出username="+userName);
		int userCount = userDao.existUsername(userName);
		if(userCount==0) {
			throw new UnknownAccountException();
		}
		User user=userDao.selectByUsername(userName);
		SimpleAuthenticationInfo info = 
				new SimpleAuthenticationInfo(user, user.getPassword(),ByteSource.Util.bytes(""),
				this.getName());
		return info;
	}
}
