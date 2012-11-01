package com.ss.entity;

// Generated 2011-3-23 11:09:37 by Hibernate Tools 3.2.2.GA

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.util.Assert;

import com.ss.CustomUserDetails;


/**
 * PubUsers generated by hbm2java
 */
public class SysUser implements CustomUserDetails,java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String username;
		
	private Boolean enabled;
	
	private Boolean issys;	
	
	private String module;
	
	private Set<SysUserRole> sysUserRole = new HashSet<SysUserRole>(0);	
	
	public Set<SysUserRole> getSysUserRole() {
		return sysUserRole;
	}

	public void setSysUserRole(Set<SysUserRole> sysUserRole) {
		this.sysUserRole = sysUserRole;
	}

	//UserDetails
    private  String password; 
    private  Set<GrantedAuthority> authorities;
    private  boolean accountNonExpired;
    private  boolean accountNonLocked;
    private  boolean credentialsNonExpired;

    
    public SysUser(){
    	
    }
    
	public SysUser(String userId, String userAccount, String userName,
			String userPassword, String userDesc, Boolean enabled,
			Boolean issys, String userDuty, String userDept, String subSystem, Set<SysUserRole> sysUsersRoleses,boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities) {

        if (((userAccount == null) || "".equals(userAccount)) || (userPassword == null)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }

        this.id = userId;
        this.username = userName;
        this.issys = issys;
        this.module = subSystem;
        this.sysUserRole = sysUsersRoleses;
        this.username = userAccount;
        this.password = userPassword;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }

	public String getUserId() {
		return this.id;
	}

	public void setUserId(String userId) {
		this.id = userId;
	}

	public String getUserName() {
		return this.username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	



	public boolean getEnabled() {
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
	

	public String getSubSystem() {
		return this.module;
	}

	public void setSubSystem(String subSystem) {
		this.module = subSystem;
	}
	



    //~ Methods ========================================================================================================

    public boolean equals(Object rhs) {
        if (!(rhs instanceof SysUser) || (rhs == null)) {
            return false;
        }

        SysUser user = (SysUser) rhs;

        if (!authorities.equals(user.authorities)) {
            return false;
        }

        return (this.getPassword().equals(user.getPassword()) && this.getUsername().equals(user.getUsername())
                && (this.isAccountNonExpired() == user.isAccountNonExpired())
                && (this.isAccountNonLocked() == user.isAccountNonLocked())
                && (this.isCredentialsNonExpired() == user.isCredentialsNonExpired())
                && (this.isEnabled() == user.isEnabled()));
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    public void setAuthorities( Collection<GrantedAuthority> authorities ){
    	this.authorities = (Set<GrantedAuthority>) authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    

    public int hashCode() {
        int code = 9792;

		if (null != getUsername() && null != getAuthorities()) {
			for (GrantedAuthority authority : getAuthorities()) {

				code = code * (authority.hashCode() % 7);
			}
		}

        if (this.getPassword() != null) {
            code = code * (this.getPassword().hashCode() % 7);
        }

        if (this.getUsername() != null) {
            code = code * (this.getUsername().hashCode() % 7);
        }

        if (this.isAccountNonExpired()) {
            code = code * -2;
        }

        if (this.isAccountNonLocked()) {
            code = code * -3;
        }

        if (this.isCredentialsNonExpired()) {
            code = code * -5;
        }

        if (this.isEnabled()) {
            code = code * -7;
        }

        return code;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities =
            new TreeSet<GrantedAuthority>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
	
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("UserSubSystem: ").append(this.module).append("; ");
        sb.append("UserIsSys: ").append(this.issys).append("; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

        if ( null !=authorities  && !authorities.isEmpty()) {
            sb.append("Granted Authorities: ");
            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }
        return sb.toString();
    }
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void main(String[] args){
    	
    }

	@Override
	public String getUserAccount() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public String getUserPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUserDesc() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public String getUserDept() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserDuty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getSysUsersRoleses() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
