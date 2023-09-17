package com.tictoc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tictoc.entity.NotificationEntity;
import com.tictoc.entity.UserEntity;
import com.tictoc.constraint.NotificationType;
import com.tictoc.entity.VideoEntity;


public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

	List<NotificationEntity> findByUserTo(UserEntity userTo);

	Optional<NotificationEntity> findByUserFromAndUserToAndNotificationType(
																UserEntity userFrom, 
																UserEntity userTo,
																NotificationType notificationType
																);
	
	Optional<NotificationEntity> findByUserFromAndVideoAndNotificationType(
																UserEntity userFrom, 
																VideoEntity video, 
																NotificationType notificationType
																);
	long countByStatusIsFalseAndUserTo(UserEntity userTo);
}
