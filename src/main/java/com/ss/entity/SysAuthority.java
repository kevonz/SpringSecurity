package com.ss.entity;

// Generated 2011-3-23 11:09:37 by Hibernate Tools 3.2.2.GA

import java.util.HashSet;
import java.util.Set;

/**
 * PubAuthorities generated by hbm2java
 */
public class SysAuthority implements java.io.Serializable {

	private String id;
	private String name;
	private String description;
	private Boolean enabled;
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

	public Set getPubRoleAuthority() {
		return pubRoleAuthority;
	}

	public void setPubRoleAuthority(Set pubRoleAuthority) {
		this.pubRoleAuthority = pubRoleAuthority;
	}

	public Set getPubAuthorityResource() {
		return pubAuthorityResource;
	}

	public void setPubAuthorityResource(Set pubAuthorityResource) {
		this.pubAuthorityResource = pubAuthorityResource;
	}

	private Boolean issys;
	private String module;
	private Set pubRoleAuthority = new HashSet(0);
	private Set pubAuthorityResource = new HashSet(0);

	public SysAuthority() {
	}

	public SysAuthority(String authorityId) {
		this.id = authorityId;
	}

	public SysAuthority(String authorityId, String authorityName,
			String authorityDesc, Boolean enabled, Boolean issys, String module,
			Set pubRolesAuthoritieses, Set pubAuthoritiesResourceses) {
		this.id = authorityId;
		this.name = authorityName;
		this.description = authorityDesc;
		this.enabled = enabled;
		this.issys = issys;
		this.module = module;
		this.pubRoleAuthority = pubRolesAuthoritieses;
		this.pubAuthorityResource = pubAuthoritiesResourceses;
	}

	public String getAuthorityId() {
		return this.id;
	}

	public void setAuthorityId(String authorityId) {
		this.id = authorityId;
	}

	public String getAuthorityName() {
		return this.name;
	}

	public void setAuthorityName(String authorityName) {
		this.name = authorityName;
	}

	public String getAuthorityDesc() {
		return this.description;
	}

	public void setAuthorityDesc(String authorityDesc) {
		this.description = authorityDesc;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getIssys() {
		return this.issys;
	}

	public void setIssys(Boolean issys) {
		this.issys = issys;
	}
	
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Set getSysRoleAuthority() {
		return this.pubRoleAuthority;
	}

	public void setSysRoleAuthority(Set pubRolesAuthoritieses) {
		this.pubRoleAuthority = pubRolesAuthoritieses;
	}

	public Set getSysAuthorityResource() {
		return this.pubAuthorityResource;
	}

	public void setSysAuthorityResource(Set pubAuthoritiesResourceses) {
		this.pubAuthorityResource = pubAuthoritiesResourceses;
	}

}
