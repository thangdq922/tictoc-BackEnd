package com.tictoc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tictoc.auth.MyUser;
import com.tictoc.entity.UserEntity;

public class SecurityUtil {
	@Autowired
	static SimpMessageHeaderAccessor accessor;

	public static Long getPrincipalId() {
		if (SecurityContextHolder.getContext().getAuthentication() == null
				|| SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
			return null;
		}
		return ((MyUser) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal()).getUser().getId();

	}

	public static UserEntity getUserCurrent() {
		if (SecurityContextHolder.getContext().getAuthentication() == null
				|| SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
			return null;
		}
		return ((MyUser) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal()).getUser();

	}

}
