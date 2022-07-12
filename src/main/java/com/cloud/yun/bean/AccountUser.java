package com.cloud.yun.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;

/**
 * @ClassName AccountUser
 * @Description AccountUser is to handle xxxx
 * @Author jack
 * @Date 7/11/2022 5:12 PM
 * @Version 1.0
 **/
public class AccountUser implements UserDetails, Serializable {

	private static final long serialVersionUID = 4310148856435268808L;
	private Long userId;

	private String password;
	private final String username;
	private final Collection<? extends GrantedAuthority> authorities;
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;
	private final boolean enabled;

	public AccountUser(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this(userId, username, password, true, true, true, true, authorities);
	}

	public AccountUser(Long userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		Assert.isTrue(username != null && !"".equals(username) && password != null, "Cannot pass null or empty values to constructor");
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = authorities;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
