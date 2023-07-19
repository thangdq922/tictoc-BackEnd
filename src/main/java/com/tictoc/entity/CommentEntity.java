package com.tictoc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class CommentEntity extends BaseEntity {

	private String comment;

	private Long likesCount;

	@ManyToOne
	@JoinColumn(name = "video_id")
	private VideoEntity video;

}
