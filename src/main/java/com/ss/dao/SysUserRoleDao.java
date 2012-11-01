package com.ss.dao;

// Generated 2011-3-23 11:09:38 by Hibernate Tools 3.2.2.GA

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ss.entity.*;

/**
 * Home object for domain model class SysUsersRoles.
 * @author Hibernate Tools
 */
public class SysUserRoleDao extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(SysUserRoleDao.class);

	public void persist(SysUserRole transientInstance) {
		log.debug("persisting SysUsersRoles instance");
		try {
			getHibernateTemplate().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(SysUserRole instance) {
		log.debug("attaching dirty SysUsersRoles instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysUserRole instance) {
		log.debug("attaching clean SysUsersRoles instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SysUserRole persistentInstance) {
		log.debug("deleting SysUsersRoles instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysUserRole merge(SysUserRole detachedInstance) {
		log.debug("merging SysUsersRoles instance");
		try {
			SysUserRole result = (SysUserRole) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SysUserRole findById(long id) {
		log.debug("getting SysUsersRoles instance with id: " + id);
		try {
			SysUserRole instance = (SysUserRole) getHibernateTemplate().get("com.ss.entity.SysUserRole", id);
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

	public List findByExample(SysUserRole instance) {
		log.debug("finding SysUserRole instance by example");
		try {
			List results = getSession().createCriteria(
					"com.ss.entity.SysUserRole").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	/**
	 * 根据角色id返回用户和角色的关联关系的实例列表。
	 *@param roleId
	 *@return
	 */
	public List<SysUserRole> getUsersRolesLst( String roleId ){
		
		try{
			
			List<SysUserRole> list = 
				getHibernateTemplate().find("from SysUserRole where role_id='" + roleId +"'");
			
			return list;
			
		}catch( RuntimeException ex){
			
			log.error("提取用户跟角色的关联关系的实例列表失败！");
			throw ex;
		}
		
	}
	
	
}
