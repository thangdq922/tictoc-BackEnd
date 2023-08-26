package com.tictoc.comment;

import java.sql.Date;

import com.tictoc.user.UserEntity;

import jakarta.validation.constraints.NotBlank;

public class CommentDTO {
	private Long Id;
	
	@NotBlank(message = " is empty")
	private String comment;
	
	private Date createdDate;
	
	private UserEntity user;
	
	private Long videoId;
	
	private Long likesCount;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Long getVideoId() {
		return videoId;
	}

	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}

	public Long getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Long likesCount) {
		this.likesCount = likesCount;
	}
	
	
	
}
