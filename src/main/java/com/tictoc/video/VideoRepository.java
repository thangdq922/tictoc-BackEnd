package com.tictoc.video;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

	Page<VideoEntity> findByCaptionContaining(String caption, Pageable pageable);

	List<VideoEntity> findByCreatedBy(Long id);

	Optional<VideoEntity> findTopByCreatedByOrderByViewsCountDesc(Long id);
}
