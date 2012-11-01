package com.ss.dao;

// Generated 2011-3-23 11:09:38 by Hibernate Tools 3.2.2.GA

import java.util.ArrayList;
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
 * Home object for domain model class SysAuthoritiesResources.
 * @see SysAuthorityResource.base.security.entity.SysAuthoritiesResources
 * @author Hibernate Tools
 */
public class SysAuthorityResourceDao extends HibernateDaoSupport{

	private static final Log log = LogFactory
			.getLog(SysAuthorityResourceDao.class);


	public void persist(SysAuthorityResource transientInstance) {
		log.debug("persisting SysAuthoritiesResources instance");
		try {
			getHibernateTemplate().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(SysAuthorityResource instance) {
		log.debug("attaching dirty SysAuthoritiesResources instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysAuthorityResource instance) {
		log.debug("attaching clean SysAuthoritiesResources instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SysAuthorityResource persistentInstance) {
		log.debug("deleting SysAuthoritiesResources instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysAuthorityResource merge(
			SysAuthorityResource detachedInstance) {
		log.debug("merging SysAuthoritiesResources instance");
		try {
			SysAuthorityResource result = (SysAuthorityResource) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SysAuthorityResource findById(long id) {
		log.debug("getting SysAuthoritiesResources instance with id: " + id);
		try {
			SysAuthorityResource instance = (SysAuthorityResource) getHibernateTemplate()
					.get("com.ss.entity.SysAuthorityResource", id);
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
	 * 得到PubAuthoritiesResources的对象列表。
	 *@author sparta 2011-3-23 下午02:33:05
	 *@return
	 */
	public List<SysAuthorityResource> getAll(){
		
		List<SysAuthorityResource> auths = new ArrayList<SysAuthorityResource>();
		
		try{
			
			auths = getHibernateTemplate()
				.find("from SysAuthorityResource");
						
			return auths;
			
		}catch( RuntimeException re ){
			log.error("find by authorities failed.", re);
			throw re;
		}
		
	}
	
	
	
	/**
	 * 根据权限id删除权限与资源之间旧有的关联关系。
	 *@author sparta 2011-4-23 上午10:14:29
	 *@param authorityId
	 */
	public void deleteOldAuthorityAndResourceRelative( String authorityId ){
		
		try{
			
			getSession().createSQLQuery("delete Sys_Authority_Resource where authority_id='" + authorityId + "'");
			
			log.info("删除权限与资源之间的关联关系成功！");
			
		}catch(RuntimeException re){
			log.error("删除权限与资源之间的关联关系失败！");
			throw re;
		}
		
	}
	

	public List findByExample(SysAuthorityResource instance) {
		log.debug("finding SysAuthoritiesResources instance by example");
		try {
			List results = getSession().createCriteria(
					"com.ss.entity.SysAuthoritiesResources").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
