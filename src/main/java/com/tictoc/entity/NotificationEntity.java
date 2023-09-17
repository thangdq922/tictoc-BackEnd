package com.tictoc.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tictoc.constraint.NotificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
@EntityListeners(AuditingEntityListener.class)
public class NotificationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	@ManyToOne
	@JoinColumn(name = "user_to_id")
	private UserEntity userTo;

	@ManyToOne
	@JoinColumn(name = "user_from_id")
	private UserEntity userFrom;

	@ManyToOne
	@JoinColumn(name = "video_id")
	private VideoEntity video;

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;
	
	private boolean status = false;

	public NotificationEntity() {
	}

	@Column(name = "createddate")
	@CreatedDate
	private Date createdDate;

	public NotificationEntity(String content, UserEntity userTo, UserEntity userFrom, VideoEntity video,
			NotificationType notificationType) {
		super();
		this.content = content;
		this.userTo = userTo;
		this.userFrom = userFrom;
		this.video = video;
		this.notificationType = notificationType;
	}

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

	public VideoEntity getVideo() {
		return video;
	}

	public void setVideo(VideoEntity video) {
		this.video = video;
	}

	public UserEntity getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(UserEntity userFrom) {
		this.userFrom = userFrom;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
}