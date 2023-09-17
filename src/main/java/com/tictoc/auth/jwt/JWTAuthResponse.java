package com.tictoc.auth.jwt;

import com.tictoc.entity.UserEntity;


public class JWTAuthResponse {

	private String accessToken;
	private String tokenType = "Bearer";

	private UserEntity data;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public UserEntity getData() {
		return data;
	}

	public void setData(UserEntity data) {
		this.data = data;
	}
	
	

}
