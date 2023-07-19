package com.tictoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tictoc.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	List<UserEntity> findByUserNameContaining(String username);
}
