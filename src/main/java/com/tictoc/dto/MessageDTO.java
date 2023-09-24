package com.tictoc.dto;

import java.util.Date;

import com.tictoc.entity.UserEntity;

public class MessageDTO {

	private Long id;

	private String content;

	private UserEntity userTo;
	
	private UserEntity userFrom;
	
	private boolean status;
	
	private Date createdDate; 
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserEntity getUserTo() {
		return userTo;
	}

	public void setUserTo(UserEntity userTo) {
		this.userTo = userTo;
	}

	public UserEntity getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(UserEntity userFrom) {
		this.userFrom = userFrom;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
