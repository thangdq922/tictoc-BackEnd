package com.tictoc.service;

import com.tictoc.dto.FollowDTO;
import com.tictoc.entity.UserEntity;

public interface FollowService {
	
	long countFollow(UserEntity user);

	void follow(FollowDTO dto);

	void unFollow(FollowDTO dto);
}
