package com.tictoc.user.follow;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tictoc.user.UserEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

	long countByUser(UserEntity user);
	
	long countByFollowing(Long following);

	Optional<FollowEntity> findByUserAndFollowing(UserEntity user, Long following);
}
