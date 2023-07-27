package com.tictoc.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tictoc.comment.CommentRepository;
import com.tictoc.common.SecurityUtil;
import com.tictoc.emotion.EmotionRepository;
import com.tictoc.user.UserRepository;

@Component
public class VideoConverter {

	@Autowired
	UserRepository userRepository;
	@Autowired
	EmotionRepository emotionRepository;
	@Autowired
	CommentRepository commentRepository;

	public VideoDTO convertToDto(VideoEntity video) {
		VideoDTO videoDTO = new VideoDTO();
		Long idCurrent = SecurityUtil.getPrincipal();
		videoDTO.setId(video.getId());
		videoDTO.setCaption(video.getCaption());
		videoDTO.setFileUrl(video.getFileUrl());
		videoDTO.setMusic(video.getMusic());
		videoDTO.setAllows(video.getAllows());
		videoDTO.setViewsCount(video.getViewsCount());
		videoDTO.setCreatedDate(new java.sql.Date(video.getCreatedDate().getTime()));
		videoDTO.setLiked(emotionRepository.findByVideoAndUser(video, idCurrent).isPresent());
		videoDTO.setLikesCount(emotionRepository.countByVideo(video));
		videoDTO.setCommentsCount(commentRepository.countByVideo(video));
		videoDTO.setUser(userRepository.findById(video.getCreatedBy()).get());
		return videoDTO;
	}

	public VideoEntity convertToEntity(VideoDTO dto) {
		VideoEntity entity = new VideoEntity();
		entity.setCaption(dto.getCaption());
		entity.setFileUrl(dto.getFileUrl());
		entity.setAllows(dto.getAllows());
		entity.setMusic(dto.getMusic());
		entity.setViewsCount(dto.getViewsCount());
		return entity;
	}

}
