package com.tictoc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "comments")
@Entity
public class CommentEntity extends BaseEntity {

	private String comments;

	private Long likesCount = (long) 0;

	@ManyToOne
	@JoinColumn(name = "video_id")
	private VideoEntity video;

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Long likesCount) {
		this.likesCount = likesCount;
	}

	public VideoEntity getVideo() {
		return video;
	}

	public void setVideo(VideoEntity video) {
		this.video = video;
	}

}
