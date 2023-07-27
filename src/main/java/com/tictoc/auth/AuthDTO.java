package com.tictoc.auth;

import lombok.Data;

@Data
public class AuthDTO {

	private String usernameOrEmail;
	private String password;
}
