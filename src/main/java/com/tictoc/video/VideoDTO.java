package com.tictoc.video;

import java.sql.Date;
import java.util.List;

import com.tictoc.user.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	
	private UserEntity user;
	
	private Date createdDate;
}
