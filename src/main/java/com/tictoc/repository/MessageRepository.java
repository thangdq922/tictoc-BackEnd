package com.tictoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tictoc.entity.MessageEnitty;
import com.tictoc.entity.UserEntity;


public interface MessageRepository extends JpaRepository<MessageEnitty, Long> {

	List<MessageEnitty> findByUserFromOrderByCreatedDateDesc(UserEntity userFrom);
	
	List<MessageEnitty> findByUserFromAndUserTo(UserEntity userFrom, UserEntity userTo);
}
