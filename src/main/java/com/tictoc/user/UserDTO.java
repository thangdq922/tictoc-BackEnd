package com.tictoc.user;

import java.sql.Date;
import java.util.List;

import com.tictoc.video.VideoDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private Long id;

	@NotBlank(message = "username have blank")
	private String userName;
	
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
	
	private Date birthDay;
	
	private VideoDTO popularVideo;
	
	private List<VideoDTO> videos;
	
	private String code;

}
