package com.tictoc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tictoc.dto.CommentDTO;
import com.tictoc.entity.CommentEntity;
import com.tictoc.repository.UserRepository;

@Component
public class CommentConverter {

	@Autowired
	UserRepository userRepository;
	
	public CommentDTO convertToDto(CommentEntity commentEntity) {
		CommentDTO dto = new CommentDTO();
		dto.setId(commentEntity.getId());
		dto.setComment(commentEntity.getComments());
		dto.setUser(userRepository.findById(commentEntity.getCreatedBy()).get());
		dto.setCreatedDate(new java.sql.Date(commentEntity.getCreatedDate().getTime()));
		dto.setLikesCount(commentEntity.getLikesCount());
		return dto;
	}
}
