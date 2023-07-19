package com.tictoc.service;

import java.util.List;

import com.tictoc.dto.CommentDTO;

public interface CommentService {

	CommentDTO saveComment(CommentDTO commentDTO);
	
	List<CommentDTO> findByComment(String content);

	List<CommentDTO> findAllComments();
	
	List<CommentDTO> findByVideo(Long id);
	
	void deleteComment(long []ids);
}

