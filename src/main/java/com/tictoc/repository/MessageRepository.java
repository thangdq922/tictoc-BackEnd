package com.tictoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tictoc.entity.MessageEnitty;
import com.tictoc.entity.UserEntity;


public interface MessageRepository extends JpaRepository<MessageEnitty, Long> {

	@Query(value = "SELECT a.* FROM messages a "
			+ "LEFT OUTER JOIN messages b "
			+ "ON a.user_from_id = b.user_from_id AND a.createddate < b.createddate "
			+ "WHERE b.id IS  NULL "
			+ "having user_from_id = :id"
			+ "order by createddate desc", nativeQuery = true)
	List<MessageEnitty> findMessageByUserFrom(@Param("id") Long userFromId);
	
	List<MessageEnitty> findByUserFromAndUserTo(UserEntity userFrom, UserEntity userTo);
	
	@Query(value = " select b.* from messages b", nativeQuery = true)
	List<MessageEnitty> bb();
}
