package com.tictoc.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity extends BaseEntity{

	@Column(unique = true)
	private String userName;

	private String name;

	private String password;

	private String email;

	private String avatar;
	
	private String bio;

	private boolean tick;
	
	private Long followingsCount;
	
	private Long followersCount;
	
	private Long likesCount;
	
	private String facebookUrl;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<RoleEntity> roles = new ArrayList<>();

}
