package com.tictoc.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tictoc.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	Optional<UserEntity> findByUserName(String userName);

	Optional<UserEntity>findByEmail(String username);

	Optional<UserEntity> findByUserNameOrEmail(String username, String email);

	Page<UserEntity> findByUserNameContaining(String username, Pageable pageable);

	Boolean existsByUserName(String username);

	Boolean existsByEmail(String email);
	
	@Query(value = "select u.* from users u "
			+ "inner join followers f "
			+ "on u.id = f.user_id "
			+ "where f.following = :id"
			, nativeQuery = true)
	Page<UserEntity> findByFollowing(@Param("id") Long following, Pageable pageable);
	
	@Query(value = "select u.* from users u "
			+ "where u.id != :id and u.id not in "
			+ "(select f.user_id from followers f "
			+ "where f.following = :id)"	
			, nativeQuery = true)
	Page<UserEntity> findSuggestedUsers(@Param("id") Long currentId, Pageable pageable);
}
