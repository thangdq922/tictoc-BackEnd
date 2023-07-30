package com.tictoc.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tictoc.auth.jwt.JWTAuthResponse;
import com.tictoc.auth.jwt.JwtTokenProvider;

@Service
public class AuthServiceImp implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public JWTAuthResponse login(AuthDTO authDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authDto.getUsernameOrEmail(), authDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);

		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		jwtAuthResponse
				.setData(((MyUser) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal()).getUser());
		return jwtAuthResponse;
	}
}
