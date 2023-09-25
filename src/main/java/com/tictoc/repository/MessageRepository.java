package com.tictoc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tictoc.entity.MessageEnitty;



public interface MessageRepository extends JpaRepository<MessageEnitty, Long> {

	@Query(value = "select * from messages a "
			+ "where a.createddate in "
			+ "(select max(b.createddate) from messages b "
			+ "group by b.room " 
			+ "having b.room like CONCAT('%', :id, '%'))",
				nativeQuery = true)
	Page<MessageEnitty> findMessageByUserFrom(@Param("id") long userFromId, Pageable pageable);
	
	Page<MessageEnitty> findByRoomOrderByCreatedDateDesc(String room, Pageable pageable);
	
	Page<MessageEnitty> findByRoomOrderByCreatedDateAsc(String room, Pageable pageable);
	
	@Query(value ="select count(status) from messages a "
			+ "where a.createddate in "
			+ "(select max(b.createddate) from messages b "
			+ "group by b.room  "
			+ "having b.room like CONCAT('%', :id, '%')) and status = false and user_to_id = :id"
			,nativeQuery = true)
	long countStatusMessage(@Param("id") long userFromId);
}
