package com.tictoc.dto;

import java.sql.Date;
import java.util.List;

public class VideoDTO {

	private Long Id;
	private String caption;
	private String fileUrl;
	private String music;
	private boolean isLiked;
	private Long likesCount;
	private Long commentsCount;
	private Long viewsCount;
	private List<String> allows;
	private UserDTO user;
	private Date createdDate;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public Long getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Long likesCount) {
		this.likesCount = likesCount;
	}

	public Long getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Long commentsCount) {
		this.commentsCount = commentsCount;
	}

	public Long getViewsCount() {
		return viewsCount;
	}

	public void setViewsCount(Long viewsCount) {
		this.viewsCount = viewsCount;
	}

	public List<String> getAllows() {
		return allows;
	}

	public void setAllows(List<String> allows) {
		this.allows = allows;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
