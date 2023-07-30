package com.tictoc.auth.jwt;

import com.tictoc.user.UserEntity;

import lombok.Data;

@Data
public class JWTAuthResponse {

	private String accessToken;
	private String tokenType = "Bearer";
	
	private UserEntity data;

}
