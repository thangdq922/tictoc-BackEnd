package com.tictoc.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tictoc.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	Optional<UserEntity> findByUserName(String userName);

	Optional<UserEntity>findByEmail(String username);

	Optional<UserEntity> findByUserNameOrEmail(String username, String email);

	Page<UserEntity> findByUserNameContaining(String username, Pageable pageable);

	Boolean existsByUserName(String username);

	Boolean existsByEmail(String email);
}
