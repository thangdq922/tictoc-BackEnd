package com.tictoc.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tictoc.entity.RoleEntity;
import com.tictoc.entity.UserEntity;

public class MyUser implements UserDetails {
	private static final long serialVersionUID = 1L;
	private UserEntity user;
	List<GrantedAuthority> authorities;

	public MyUser(UserEntity user) {
		this.user = user;
		this.authorities = new ArrayList<>();
		for (RoleEntity role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getCode()));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.user.getUserName();
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public UserEntity getUser() {
		return this.user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
