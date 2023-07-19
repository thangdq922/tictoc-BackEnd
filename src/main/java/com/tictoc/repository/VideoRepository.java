package com.tictoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tictoc.entity.VideoEntity;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
	
	List<VideoEntity> findByCaptionContaining(String caption);;

}
