package com.tictoc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "followers")
@Getter
@Setter
public class FollowEntity extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	
}
