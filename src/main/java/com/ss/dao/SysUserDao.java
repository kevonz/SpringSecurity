package com.ss.dao;

// Generated 2011-3-23 11:09:38 by Hibernate Tools 3.2.2.GA

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.ss.entity.*;
import com.utils.Util;

/**
 * Home object for domain model class SysUsers.
 * 
 * @see avatar.base.security.entity.SysUser
 * @author Hibernate Tools
 */
public class SysUserDao extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(SysUserDao.class);

	// 对用户输入的密码进行MD5编码
	private PasswordEncoder passwordEncoder;

	// 通过SysrolesDao获得角色实例。
	private SysRoleDao sysRoleDao;

	@SuppressWarnings("null")
	public void persist(SysUser transientInstance, String[] rolesArr ) {
		log.debug("持久化一个用户实例！");
		try {
			// 先将该用户对应的所有角色
			Set<SysUserRole> userRoles = new HashSet<SysUserRole>();

			// 角色实例
			SysRole role = null;

			for (String roleId : rolesArr) {

				if (null != roleId && !"".equals(roleId)) {			
					
					role = sysRoleDao.findById(roleId);
					
					SysUserRole userRole = new SysUserRole(Util
							.getPkId(), transientInstance, role, true);

					userRoles.add( userRole );
				}

			}

			transientInstance.setUserId( Util.getPkId()+"");
			
			// 密码通过盐值加密以备存储入数据库
			String newPassword = passwordEncoder.encodePassword(transientInstance.getUserPassword().trim(),transientInstance.getUserName().trim());

		//	transientInstance.setUserPassword(newPassword);

			if(null != userRoles )
				transientInstance.setSysUserRole(userRoles);
			
			getHibernateTemplate().persist(transientInstance);
			
			log.debug("持久化实例成功！");
		} catch (RuntimeException re) {
			log.error("持久化用户实例失败！", re);
			throw re;
		}
	}

	/*
	 * 允许当前用户修改密码
	 */
	// public void changePassword(String userId,String oldPassword, String
	// newPassword) {
	// UserDetails userDetails = (UserDetails) SecurityContextHolder
	// .getContext().getAuthentication().getPrincipal();
	// String username = userDetails.getUsername();
	//
	// String encodedOldPassword = passwordEncoder.encodePassword(oldPassword,
	// username);
	// String encodedNewPassword = passwordEncoder.encodePassword(newPassword,
	// username);
	// userDetailsManager.changePassword(encodedOldPassword,
	// encodedNewPassword);
	// }
	/*
	 * 保存或更新一个用户实例，并且保存用户和角色之间的关联关系。
	 */
	@SuppressWarnings("null")
	public void attachDirty(SysUser instance, String[] rolesArr, 
			String tempPassword ) {
		log.debug("更新一个用户实例");
		try {

			// 先将该用户对应的所有角色
			Set<SysUserRole> userRoles = new HashSet<SysUserRole>();

			// 角色实例
			SysRole role = new SysRole();

			for (String roleId : rolesArr) {
					
					if (null != roleId && !"".equals(roleId)) {

						role = sysRoleDao.findById(roleId);

						userRoles.add((SysUserRole) new SysUserRole(Util
								.getPkId(), instance, role, true));
					}

			}
			

//			if( null == instance.getUserId() || "".equals( instance.getUserId()))
//				instance.setUserId( Util.getPkId()+"");
			
			//若密码已经更新
			if (tempPassword.equals(instance.getUserPassword())) {

				// 密码通过盐值加密以备存储入数据库
				String newPassword = passwordEncoder.encodePassword(instance
						.getUserPassword().trim(), instance.getUserName()
						.trim());

	//			instance.setUserPassword(newPassword);
			}

			if(null != userRoles )
				instance.setSysUserRole(userRoles);

			getHibernateTemplate().saveOrUpdate(instance);

			log.debug("更新用户实例成功！");

		} catch (RuntimeException re) {
			log.error("更新用户实例失败！", re);
			throw re;
		}
	}

	public void attachClean(SysUser instance) {
		log.debug("attaching clean SysUsers instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SysUser persistentInstance) {
		log.debug("deleting SysUsers instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/*
	 * 根据用户id删除一个用户，非物理删除，而是一个逻辑删除，置enabled为false。
	 */
	public void delete(String userId) {
		log.debug("根据一个用户id删除一个用户，角色id为：" + userId);
		try {

			SysUser user = findById(userId);
			user.setEnabled( false );
			getHibernateTemplate().saveOrUpdate(user);

			log.debug("删除成功！");
		} catch (RuntimeException re) {

			log.error("删除失败！", re);
			throw re;
		}
	}

	public SysUser merge(SysUser detachedInstance) {
		log.debug("merging SysUsers instance");
		try {
			SysUser result = (SysUser) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SysUser findById(java.lang.String id) {
		log.debug("getting SysUsers instance with id: " + id);
		try {
			SysUser instance = (SysUser) getHibernateTemplate().get(
					"avatar.base.security.entity.SysUsers", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * 根据用户账号返回SysUsers实例对象。
	 * 
	 * @author sparta 2011-4-8 下午01:53:05
	 *@param userAccount
	 *            用户账号，比如admin等。
	 *@return SysUsers实例对象。
	 */
	public SysUser findByUserAccount(String userAccount) {
		log.debug("根据UserAccount查找SysUsers实例对象: " + userAccount);
		try {
			SysUser instance = (SysUser) getHibernateTemplate().find(
					"from SysUser where username='" + userAccount + "'")
					.get(0);
			if (instance == null) {
				log.debug("没有相匹配的SysUsers实例对象！");
				instance = new SysUser();
			} else {
				log.debug("相匹配的SysUsers实例对象被找到！");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("findByUserAccount() 错误！", re);
			throw re;
		}
	}

	/*
	 * 得到用户列表，参数列表（查询条件）分别为用户所在单位、用户账号、用户名称、 用户所在的子系统、跟用户相关联的角色。
	 */
	public List<SysUser> findUserLst(String qryUnit, String qryUserAccount,
			String qryUsername, String qryModule, String qryRole) {

		List<SysUser> list = null;
		
		String sql = "select users from SysUsers users where users.enabled = 1 ";
		
		//查询与用户相关联的角色列表。
		if( null != qryRole && !"".equals( qryRole )){
		
			sql = "select users from SysUsers users, SysUsersRoles usersRoles "
				+ "where users.userId = usersRoles.sysUsers.userId and users.enabled = 1 ";
		}
		
		try {

			if (null != qryUnit && !"".equals(qryUnit)) {
				sql += "and users.userDept='" + qryUnit + "' ";
			}

			if (null != qryUserAccount && !"".equals(qryUserAccount)) {
				sql += "and users.userAccount='" + qryUserAccount + "' ";
			}

			if (null != qryUsername && !"".equals(qryUsername)) {
				sql += "and users.userName = '" + qryUsername + "' ";
			}

			if (null != qryModule && !"".equals(qryModule) && !"00".equals( qryModule )) {
				sql += "and users.subSystem='" + qryModule + "' ";
			}

			if (null != qryRole && !"".equals(qryRole) && !"00".equals( qryRole )) {
				sql += "and usersRoles.sysRoles.roleId ='" + qryRole + "'";
			}

			list = getHibernateTemplate().find(sql);

			return list;

		} catch (RuntimeException re) {
			log.error("findUserLst() 错误！", re);
			throw re;
		}

	}



	/*
	 * 通过系统模块得到角色列表。
	 */
	public HashMap getRolesMap(String xtmk) {

		HashMap rolesMap = new HashMap();

		try {
			String hql = "from SysRoles where module='" + xtmk + "'";

			List<SysRole> list = getHibernateTemplate().find(hql);

			for (SysRole role : list) {
				rolesMap.put(role.getRoleId(), role.getRoleDesc());
			}
		} catch (RuntimeException re) {
			log.error("提取角色Map失败！", re);
			throw re;
		}

		return rolesMap;
	}

	/**
	 *@author sparta 2011-3-30 下午03:51:48
	 *@param username
	 *@param session
	 *@return
	 */
	public List<GrantedAuthority> loadUserAuthoritiesByName(String username) {

		try {

			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

			List<String> query1 = loadUserAuthorities(username);

			for (String roleName : query1) {
				GrantedAuthorityImpl authority = new GrantedAuthorityImpl(
						roleName);
				auths.add(authority);
			}

			return auths;

		} catch (RuntimeException re) {
			log.error("find by authorities by username failed.", re);
			throw re;
		}
	}

	/**
	 *@author sparta 2011-3-30 下午03:51:48
	 *@param username
	 *@param session
	 *@return
	 */
	public List<String> loadUserAuthorities(final String username) {

		try {

			return this.getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public List<SysAuthority> doInHibernate(
								Session session) throws HibernateException,
								SQLException {
							Query query = session
									.createSQLQuery("select a.name "
											+ "from Sys_Authority a, Sys_Role_Authority b, Sys_User_Role c, Sys_User d "
											+ "where   a.id = b.authority_id and b.role_id = c.role_id  "
											+ "and c.user_id = d.id and  d.username = '"
											+ username + "'");

							return query.list();
						}
					});

		} catch (RuntimeException re) {
			log.error("find by authorities by username failed.", re);
			throw re;
		}
	}

	// 注入密码MD5编码
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	// 注入sysRolesDao
	public void setSysRoleDao(SysRoleDao sysRolesDao) {
		this.sysRoleDao = sysRolesDao;
	}

	public SysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

}
