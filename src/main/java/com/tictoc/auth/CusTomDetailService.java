package com.tictoc.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tictoc.user.UserEntity;
import com.tictoc.user.UserRepository;

@Service
public class CusTomDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail).get();

		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new MyUser(userEntity);

	}

}
