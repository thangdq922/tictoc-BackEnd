package com.tictoc.common;

import org.springframework.security.core.context.SecurityContextHolder;

import com.tictoc.auth.MyUser;

public class SecurityUtil {
	public static Long getPrincipal() {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
			return null;
		}
		return ((MyUser) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal()).getUser().getId();

	}
}
