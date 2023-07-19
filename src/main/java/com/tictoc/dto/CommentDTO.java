package com.tictoc.dto;

import java.sql.Date;

import com.tictoc.entity.UserEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
	private Long Id;
	
	@NotBlank(message = " is empty")
	private String comment;
	
	private Date createdDate;
	
	private UserEntity user;
	
	private Long videoId;
	
	
	
}
