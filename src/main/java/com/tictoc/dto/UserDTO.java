package com.tictoc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private Long id;

	@NotBlank(message = "username have blank")
	private String userName;
	@NotEmpty(message = "name is empty")
	private String name;
	@NotBlank(message = "password have blank")
	private String password;
	@NotBlank(message = "email have blank")
	@Email(message = "Email is not valid", regexp = ".+[@].+[\\.].+")
	private String email;

	private String avatar;

	private String bio;

	private boolean tick;
	
	private boolean isFollowed;

	private Long followingsCount;

	private Long followersCount;

	private Long likesCount;

	private String facebookUrl;

}
