package com.tictoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tictoc.entity.EmotionEntity;
import com.tictoc.entity.VideoEntity;

public interface EmotionRepository extends JpaRepository<EmotionEntity, Long> {
	long countByVideo(VideoEntity video);
	
	EmotionEntity findByVideoAndCreatedBy(VideoEntity video, Long id);
	
//	List<EmotionEntity> findByVideo(VideoEntity video);
}
