package com.tictoc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tictoc.entity.MessageEnitty;


public interface MessageRepository extends JpaRepository<MessageEnitty, Long> {

	@Query(value = "SELECT a.* FROM messages a "
			+ "LEFT OUTER JOIN messages b "
			+ "ON a.user_to_id = b.user_to_id AND a.createddate < b.createddate "
			+ "WHERE b.id IS  NULL "
			+ "having user_from_id = :id", 
				nativeQuery = true)
	Page<MessageEnitty> findMessageByUserFrom(@Param("id") long userFromId, Pageable pageable);
	
	List<MessageEnitty> findByUserFromAndUserTo(UserEntity userFrom, UserEntity userTo);
	
}
