package com.tictoc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tictoc.entity.VideoEntity;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

	Page<VideoEntity> findByCaptionContaining(String caption, Pageable pageable);

	List<VideoEntity> findByCreatedBy(Long id);
	
	@Query(value = "select * from videos v "
			+ "inner join followers f "
			+ "on v.createdby = f.user_id "
			+ "where f.following = :id"
			, nativeQuery = true)
	Page<VideoEntity> findVideoFollowing(@Param("id") Long currentId, Pageable pageable);

	Optional<VideoEntity> findTopByCreatedByOrderByViewsCountDesc(Long id);
}
