package com.ss.entity;

// Generated 2011-3-23 11:09:37 by Hibernate Tools 3.2.2.GA

import java.util.HashSet;
import java.util.Set;

/**
 * PubResources generated by hbm2java
 */
public class SysResource implements java.io.Serializable {

	private String id;
	private String name;
	private String description;	
	private String type;
	private String url;
	private Boolean priority;	
	
	private Integer enabled;
	private Integer issys;	
	private String module;
	private Set pubAuthorityResource = new HashSet(0);
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public SysResource() {
	}

	public SysResource(String resourceId) {
		this.id = resourceId;
	}

	public SysResource(String resourceId, String resourceName,
			String resourceDesc, String resourceType, String resourceString,
			Boolean priority, Integer enabled, Integer issys, String module,
			Set pubAuthoritiesResourceses) {
		this.id = resourceId;
		this.name = resourceName;
		this.description = resourceDesc;
		this.type = resourceType;
		this.url = resourceString;
		this.priority = priority;
		this.enabled = enabled;
		this.issys = issys;
		this.module = module;
		this.pubAuthorityResource = pubAuthoritiesResourceses;
	}

	public Set getPubAuthorityResource() {
		return pubAuthorityResource;
	}

	public void setPubAuthorityResource(Set pubAuthorityResource) {
		this.pubAuthorityResource = pubAuthorityResource;
	}

	public String getResourceId() {
		return this.id;
	}

	public void setResourceId(String resourceId) {
		this.id = resourceId;
	}

	public String getResourceName() {
		return this.name;
	}

	public void setResourceName(String resourceName) {
		this.name = resourceName;
	}

	public String getResourceDesc() {
		return this.description;
	}

	public void setResourceDesc(String resourceDesc) {
		this.description = resourceDesc;
	}

	public String getResourceType() {
		return this.type;
	}

	public void setResourceType(String resourceType) {
		this.type = resourceType;
	}

	public String getResourceString() {
		return this.url;
	}

	public void setResourceString(String resourceString) {
		this.url = resourceString;
	}

	public Boolean getPriority() {
		return this.priority;
	}

	public void setPriority(Boolean priority) {
		this.priority = priority;
	}

	public Integer getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getIssys() {
		return this.issys;
	}

	public void setIssys(Integer issys) {
		this.issys = issys;
	}
	
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Set getSysAuthorityResource() {
		return this.pubAuthorityResource;
	}

	public void setSysAuthorityResource(Set pubAuthoritiesResourceses) {
		this.pubAuthorityResource = pubAuthoritiesResourceses;
	}

}
