package com.tictoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tictoc.entity.FollowEntity;
import com.tictoc.entity.UserEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

	long countByUser(UserEntity user);

	FollowEntity findByUserAndCreatedBy(UserEntity user, Object createdBy);
}
