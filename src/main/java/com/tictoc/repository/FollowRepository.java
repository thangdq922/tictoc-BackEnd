package com.tictoc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tictoc.entity.FollowEntity;
import com.tictoc.entity.UserEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

	long countByUser(UserEntity user);
	
	long countByFollowing(Long following);

	Optional<FollowEntity> findByUserAndFollowing(UserEntity user, Long following);
	
	List<FollowEntity> findByFollowing(Long following);
}
