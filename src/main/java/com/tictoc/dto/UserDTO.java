package com.tictoc.dto;

import java.sql.Date;
import java.util.List;

public class UserDTO {
	private Long id;
	private String userName;
	private String name;
	private String password;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public boolean isTick() {
		return tick;
	}

	public void setTick(boolean tick) {
		this.tick = tick;
	}

	public boolean isFollowed() {
		return isFollowed;
	}

	public void setFollowed(boolean isFollowed) {
		this.isFollowed = isFollowed;
	}

	public Long getFollowingsCount() {
		return followingsCount;
	}

	public void setFollowingsCount(Long followingsCount) {
		this.followingsCount = followingsCount;
	}

	public Long getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(Long followersCount) {
		this.followersCount = followersCount;
	}

	public Long getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Long likesCount) {
		this.likesCount = likesCount;
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public VideoDTO getPopularVideo() {
		return popularVideo;
	}

	public void setPopularVideo(VideoDTO popularVideo) {
		this.popularVideo = popularVideo;
	}

	public List<VideoDTO> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoDTO> videos) {
		this.videos = videos;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
