package com.ss.dao;

// Generated 2011-3-23 11:09:38 by Hibernate Tools 3.2.2.GA

import java.util.HashMap;
import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ss.entity.SysAuthority;
import com.ss.entity.SysAuthorityResource;
import com.ss.entity.SysResource;
import com.utils.Util;


/**
 * Home object for domain model class SysAuthorities.
 * @see SysAuthority.base.security.entity.SysAuthorities
 * @author Hibernate Tools
 */
public class SysAuthorityDao extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(SysAuthorityDao.class);
	
	//资源dao
	private SysResourceDao sysResourceDao;
	
	//角色和权限的对应关系的dao
	private SysRoleAuthorityDao sysRoleAuthorityDao;
	
	//权限和资源对应关系的dao
	private SysAuthorityResourceDao sysAuthorityResourceDao;

	public SysResourceDao getSysResourceDao() {
		return sysResourceDao;
	}

	public void setSysResourceDao(SysResourceDao sysResourceDao) {
		this.sysResourceDao = sysResourceDao;
	}

	public SysRoleAuthorityDao getSysRoleAuthorityDao() {
		return sysRoleAuthorityDao;
	}

	public void setSysRoleAuthorityDao(SysRoleAuthorityDao sysRoleAuthorityDao) {
		this.sysRoleAuthorityDao = sysRoleAuthorityDao;
	}

	public SysAuthorityResourceDao getSysAuthorityResourceDao() {
		return sysAuthorityResourceDao;
	}

	public void setSysAuthorityResourceDao(
			SysAuthorityResourceDao sysAuthorityResourceDao) {
		this.sysAuthorityResourceDao = sysAuthorityResourceDao;
	}

	/*
	 * 将从页面返回的权限对象进行保存。
	 */
	public void persist(SysAuthority transientInstance) {

		log.debug("权限 " + transientInstance.getAuthorityDesc() + " 持久化!");

		try {

			// 为写入的权限设置唯一id
			transientInstance.setAuthorityId(Util.getPkId() + "");
			
			String authorityName = transientInstance.getAuthorityName();

			// 当权限标识前缀不为"AUTH_"时，要添加该标识。
			if (authorityName.startsWith("AUTH")) {

				if (!authorityName.startsWith("AUTH_")) {
					authorityName = authorityName.substring(0, 4) + "_"
							+ authorityName.substring(4);
					transientInstance.setAuthorityName(authorityName);
				}

			} else {
				transientInstance.setAuthorityName("AUTH_" + authorityName);
			}

			// 持久化
			getHibernateTemplate().persist(transientInstance);

			log.debug("权限 " + transientInstance.getAuthorityDesc() + " 持久化成功！");

		} catch (RuntimeException re) {

			log.error("权限 " + transientInstance.getAuthorityDesc() + " 持久化失败！", re);
			throw re;

		}
	}

	/*
	 * 对权限实例进行更新。
	 */
	public void attachDirty(SysAuthority instance) {

		log.debug("更新权限" + instance.getAuthorityName() + "!");

		try {

			getHibernateTemplate().saveOrUpdate(instance);
			
			log.debug("更新权限 " + instance.getAuthorityName() + "成功!");

		} catch (RuntimeException re) {

			log.error("更新权限 " + instance.getAuthorityName() + "失败!", re);
			throw re;

		}
	}

	public void attachClean(SysAuthority instance) {
		log.debug("attaching clean SysAuthorities instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}


	
	/**
	 * 根据权限id删除该权限，角色和权限，权限和资源的关系。
	 * 
	 * @author sparta 2011-4-10 下午03:31:22
	 *@param authorityId
	 */
	public void delete(String authorityId) {
		log.debug("根据权限id删除该权限！权限id为 ： " + authorityId);
		try {
			
			SysAuthority authority = findById( authorityId );
			
			getHibernateTemplate().delete( authority );
		
			log.debug("权限id" + authorityId + "删除成功！");
		} catch (RuntimeException re) {
			log.error("权限id" + authorityId + "删除失败！", re);
			throw re;
		}
	}


	/**
	 * 通过id得以权限的实例。
	 *@author sparta 2011-4-20 上午09:17:02
	 *@param id
	 *@return
	 */
	public SysAuthority findById( String id ) {
		log.debug("通过id得到权限类的实例: " + id);
		try {
			
			SysAuthority instance = (SysAuthority) getHibernateTemplate().find( "from SysAuthorities where authority_id='" + id + "'").get(0);
			
			if (instance == null) {
				log.debug("该权限类不存在！");
			} else {
				log.debug("成功找到该权限类的实例！");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("查找权限类实例失败！", re);
			throw re;
		}
	}
	
	/**
	 * 根据平台中的子系统返回权限列表。
	 * 
	 * @author sparta 2011-4-23 下午18:32:38
	 *@return
	 */
	public List<SysAuthority> findAuthoritiesLst(String xtmk) {
		try {
			List<SysAuthority> results = getHibernateTemplate().find("from SysAuthorities where module='" + xtmk + "'");

			return results;

		} catch (RuntimeException re) {

			throw re;
		}
	}
	
	/**
	 * 根据权限id，得到以资源id为键，"true"为值的Map。
	 * 
	 * @author sparta 2011-4-23 下午18:15:21
	 *@param authorityId
	 *@return
	 */
	public HashMap getSelectedResourcesMap(String authorityId) {
		try {
		
			List<String> results = getSession().createSQLQuery("select a.id " +
					"from Sys_Resource a, Sys_Authority_Resource b " +
					"where a.id = b.resource_id and b.Authority_id='"+
					authorityId +"' and a.enabled=1").list();

			HashMap<String,String> hashMap = new HashMap<String,String>(0);

			for (String resourcesId : results) {
				hashMap.put( resourcesId, "true" );
			}
			
			return hashMap;
		} catch (RuntimeException re) {

			throw re;
		}
	}

	/*
	 * 保存权限和资源之间一对多的关系。
	 */
	public boolean saveAuthorityAndReSource(String authorityId, String[] resourceskey) {

		SysAuthority sysAuthorities = findById(authorityId);
		SysResource sysResources = null;
		SysAuthorityResource sysAuthoritiesResources;
		
		//在重新设置之前先删除之前所拥有的全部对应关系
		sysAuthorityResourceDao.deleteOldAuthorityAndResourceRelative( authorityId );

		try {
			/*
			 * 将用户在前台通过checkbox选中的所有权限id提取出来。
			 * 在进行保存之前，首先要通过id提取该权限的实例，
			 * 为角色权限关联表sysRolesAuthorities的setSysAuthorities()提供数据。
			 * 最后通过hibernate的save()方法保存新建的sysRolesAuthorities实例。
			 */
			for (String resId : resourceskey) {
				
				sysResources = sysResourceDao.findById( resId );
				
				/*
				 * 生成一个新的实例很重要，否则会报
				 * identifier of an instance of xxx  
				 * was altered from xxx to xxx"的异常。
				 */
				sysAuthoritiesResources = new SysAuthorityResource();
				sysAuthoritiesResources.setId( Util.getPkId() );
				sysAuthoritiesResources.setSysResources( sysResources );
				sysAuthoritiesResources.setSysAuthorities( sysAuthorities );
				sysAuthoritiesResources.setEnabled(true);

				getHibernateTemplate().save( sysAuthoritiesResources );

			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	}
	
	
	/**
	 * 得到资源列表。
	 */
	public List<SysResource> getResourcesList() {
		try {

			List<SysResource> results = getHibernateTemplate().find("from SysResources where enabled=1");

			return results;

		} catch (RuntimeException re) {

			throw re;
		}
	}
	
}
