package com.tictoc.auth;

import com.tictoc.auth.jwt.JWTAuthResponse;

public interface AuthService {

	JWTAuthResponse login(AuthDTO authDto);
}
