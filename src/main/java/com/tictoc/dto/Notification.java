package com.tictoc.dto;

import java.sql.Date;

import com.tictoc.constraint.NotificationType;
import com.tictoc.entity.UserEntity;

public class Notification {

	private Long id;

	private String content;

	private UserEntity userTo;

	private UserEntity userFrom;

	private VideoDTO video;

	private NotificationType notificationType;

	private Date createdDate;
	
	private long countNotRead;

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

	public VideoDTO getVideo() {
		return video;
	}

	public void setVideo(VideoDTO video) {
		this.video = video;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public long getCountNotRead() {
		return countNotRead;
	}

	public void setCountNotRead(long countNotRead) {
		this.countNotRead = countNotRead;
	}

}