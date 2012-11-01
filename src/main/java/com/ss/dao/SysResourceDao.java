package com.ss.dao;
// Generated 2011-3-23 11:09:38 by Hibernate Tools 3.2.2.GA



import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ss.entity.SysResource;
import com.utils.Util;



/**
 * Home object for domain model class SysUsers.
 * @see SysUser.base.security.entity.SysUsers
 * @author Hibernate Tools
 */
public class SysResourceDao extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(SysUserDao.class);


	/*
	 * 持久化资源。
	 */
	public void persist( SysResource transientInstance ) {
		
		log.debug("持久化一个资源实例！");
		try {
			
			transientInstance.setResourceId( Util.getPkId()+"" );
			
			getHibernateTemplate().persist( transientInstance );
			
			log.debug("持久化资源实例成功！");
			
		} catch (RuntimeException re) {
			log.error("持久化资源实例失败！", re);
			throw re;
		}
	}
	

	/*
	 * 保存或更新一个资源实例。
	 */
	public void attachDirty( SysResource instance ) {
		log.debug("更新一个资源实例");
		try {
			
			getHibernateTemplate().saveOrUpdate( instance );
			
			log.debug("更新资源实例成功！");
			
		} catch (RuntimeException re) {
			log.error("更新资源实例失败！", re);
			throw re;
		}
	}
	
	/*
	 * 根据资源id删除一个资源，非物理删除，而是一个逻辑删除，置enabled为false。
	 */
	public void delete( String resourceId ) {
		log.debug("根据一个资源id删除一个用户，资源id为：" + resourceId );
		try {
			
			SysResource resource = findById( resourceId );
			
			resource.setEnabled( 0 );
//			getHibernateTemplate().saveOrUpdate( resource );
			
			log.debug("删除成功！");
		} catch (RuntimeException re) {
			
			log.error("删除失败！", re);
			throw re;
		}
	}

	
	/*
	 * 根据资源对象删除一个对象，非物理删除，而是一个逻辑删除，置enabled为false。
	 */
	public void delete( SysResource resource ) {
		log.debug("根据一个资源id删除一个用户，资源id为：" + resource.getResourceId() );
		try {
			
//			SysResources resource = findById( resourceId );
			
			resource.setEnabled( 0 );
//			getHibernateTemplate().saveOrUpdate( resource );
			
			log.debug("删除成功！");
		} catch (RuntimeException re) {
			
			log.error("删除失败！", re);
			throw re;
		}
	}

	public SysResource findById(String id) {
		log.debug("getting SysUsers instance with id: " + id);
		try {
			SysResource instance = (SysResource) getHibernateTemplate()
					.find("from SysResource where id='" + id +"'").get(0);
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
	

	/*
	 * 得到资源列表，参数列表在实例中包含着，包括资源号、资源名称、资源类型、资源地址。
	 */
	public List<SysResource> findResourcesLst( SysResource resource ){
			
		List<SysResource> list = null ;
		String sql = "from SysResource where enabled=1 ";
		try{
			if(!"".equals( resource.getResourceName() )){
				sql += "and name like '" + resource.getResourceName() +"' ";
			}
			
			if(!"".equals( resource.getResourceDesc() )){
				sql += "and description like '" + resource.getResourceDesc() +"' ";
			}
			
			if(!"".equals( resource.getResourceType() )){
				sql += "and type = '" + resource.getResourceType() +"' ";
			}
			
			if(!"".equals( resource.getResourceString() )){
				sql += "and url like '%" + resource.getResourceString() + "%' ";
			}

			
			list = getHibernateTemplate().find( sql );
			
			
		}catch (RuntimeException re) {
			log.error("findByUserAccount() 错误！", re);
			throw re;
		}
		
		return list;
		
	}


}
