package com.ss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.util.AntUrlPathMatcher;
//import org.springframework.security.web.util.UrlMatcher;
import org.springframework.stereotype.Service;

import com.ss.dao.SysAuthorityResourceDao;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应角色的定义。
 * 
 */
@Service
public class CustomInvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource {

	@Autowired
	private SysAuthorityResourceDao pubAuthoritiesResourcesDao;

//	private UrlMatcher urlMatcher = new AntUrlPathMatcher();

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public CustomInvocationSecurityMetadataSourceService() {
		loadResourceDefine();
	}

	private void loadResourceDefine() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");

		SessionFactory sessionFactory = (SessionFactory) context
				.getBean("sessionFactory");

		Session session = sessionFactory.openSession();

		String username = "";
		String sql = "";
		

		// 在Web服务器启动时，提取系统中的所有权限。
		sql = "select name from sys_authority";

		List<String> query = session.createSQLQuery(sql).list();

		/*
		 * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
		 * sparta
		 */
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

		for (String auth : query) {
			ConfigAttribute ca = new SecurityConfig(auth);

			@SuppressWarnings("unchecked")
			List<String> query1 = session
					.createSQLQuery(
							"select b.url "
									+ "from Sys_Authority_Resource a, Sys_Resource b, "
									+ "Sys_authority c where a.resource_id = b.id "
									+ "and a.authority_id=c.id and c.name='"
									+ auth + "'").list();

			for (String res : query1) {
				String url = res;
					System.out.println("#####find out url:"+url);
				/*
				 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中。
				 */
				if (resourceMap.containsKey(url)) {

					Collection<ConfigAttribute> value = resourceMap.get(url);
					value.add(ca);
					resourceMap.put(url, value);
				} else {
					Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
					atts.add(ca);
					resourceMap.put(url, atts);
				}

			}

		}

	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {

		return null;
	}

	// 根据URL，找到相关的权限配置。
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// object 是一个URL，被用户请求的url。
		String url = ((FilterInvocation) object).getRequestUrl();		
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        System.out.println("@@@run to this#1");
		Iterator<String> ite = resourceMap.keySet().iterator();

		while (ite.hasNext()) {
			String resURL = ite.next();
			System.out.println("@@@resURL:"+resURL);
			if(resURL.equalsIgnoreCase(url)){
				return resourceMap.get(resURL);
			}
		/*	if (urlMatcher.pathMatchesUrl(url, resURL)) {

				return resourceMap.get(resURL);
			}*/
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {

		return true;
	}

}
