package com.tictoc.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tictoc.video.VideoEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	List<CommentEntity> findByCommentsContaining(String content);

	List<CommentEntity> findByVideo(VideoEntity video);
	
	long countByVideo(VideoEntity video);

}
