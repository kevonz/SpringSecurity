package com.ss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ss.dao.SysAuthorityResourceDao;
import com.ss.dao.SysUserDao;
import com.ss.entity.SysUser;
import com.ss.entity.SysUserRole;


/**
 *该类的主要作用是为Spring Security提供一个经过用户认证后的UserDetails。
 *该UserDetails包括用户名、密码、是否可用、是否过期等信息。
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private SysAuthorityResourceDao pubAuthorityResourceDao;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserCache userCache;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		
		if( null == sysUserDao ){
			sysUserDao  = new SysUserDao();
		}
		
		//得到用户的权限
		auths = sysUserDao.loadUserAuthoritiesByName( username );
		
		//根据用户名取得一个SysUsers对象，以获取该用户的其他信息。
		SysUser user = sysUserDao.findByUserAccount( username );
			
		return new SysUser( user.getUserId(), user.getUserAccount(), user.getUserName(),
				 user.getUserPassword(),user.getUserDesc(), true, false,
				 user.getUserDuty(), user.getUserDept(), user.getSubSystem(), 
				 new HashSet<SysUserRole>(0), true, true, true, auths);
	}
		
	//set PubUsersDao
	public void setSysUserDao( SysUserDao sysUsersDao ){
		this.sysUserDao = sysUsersDao;
		
	}
	
	public SysUserDao getSysUsersDao(){
		return sysUserDao;
	}
	
	
	//set PubAuthoritiesResourcesHome
	public void setSysAuthorityResourceDao( SysAuthorityResourceDao pubAuthorityResourceDao ){
		this.pubAuthorityResourceDao = pubAuthorityResourceDao;
		
	}
	
	public SysAuthorityResourceDao getSysAuthorityResourceDao(){
		return pubAuthorityResourceDao;
		
	}
	
	//set DataSource
	public void setDataSource( DataSource dataSource ){
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource(){
		return dataSource;
	}
	
	//设置用户缓存功能。
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }
    
    public UserCache getUserCache(){
    	return this.userCache;
    }
	
}
