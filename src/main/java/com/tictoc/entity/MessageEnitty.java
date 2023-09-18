package com.tictoc.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
@EntityListeners(AuditingEntityListener.class)
public class MessageEnitty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	@ManyToOne
	@JoinColumn(name = "user_to_id")
	private UserEntity userTo;

	@CreatedBy
	@ManyToOne
	@JoinColumn(name = "user_from_id")
	private UserEntity userFrom;

	private boolean status = false;

	@Column(name = "createddate")
	@CreatedDate
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
