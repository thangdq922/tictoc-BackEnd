package com.tictoc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "emotions")
@Getter
@Setter
public class EmotionEntity extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "video_id")
	private VideoEntity video;

	
}
