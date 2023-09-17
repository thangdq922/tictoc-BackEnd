package com.tictoc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tictoc.entity.EmotionEntity;
import com.tictoc.entity.VideoEntity;

public interface EmotionRepository extends JpaRepository<EmotionEntity, Long> {
	long countByVideo(VideoEntity video);

	Optional<EmotionEntity> findByVideoAndUser(VideoEntity video, Long id);

}
