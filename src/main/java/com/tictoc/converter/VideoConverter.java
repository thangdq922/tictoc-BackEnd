package com.tictoc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tictoc.dto.VideoDTO;
import com.tictoc.entity.VideoEntity;
import com.tictoc.repository.CommentRepository;
import com.tictoc.repository.EmotionRepository;
import com.tictoc.repository.UserRepository;

@Component
public class VideoConverter {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserConverter userConverter;
	@Autowired
	EmotionRepository emotionRepository;
	@Autowired
	CommentRepository commentRepository;

	public VideoDTO convertToDto(VideoEntity video, Long idCurrent) {
		VideoDTO videoDTO = new VideoDTO();
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
		videoDTO.setUser(
				userConverter.convertToDto(userRepository.findById(video.getCreatedBy()).orElse(null), idCurrent));
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
