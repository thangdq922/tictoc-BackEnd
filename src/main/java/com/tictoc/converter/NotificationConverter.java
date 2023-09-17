package com.tictoc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tictoc.dto.Notification;
import com.tictoc.entity.NotificationEntity;
import com.tictoc.repository.CommentRepository;
import com.tictoc.repository.EmotionRepository;
import com.tictoc.repository.UserRepository;

@Component
public class NotificationConverter {
	@Autowired
	UserRepository userRepository;
	@Autowired
	VideoConverter videoConverter;
	@Autowired
	EmotionRepository emotionRepository;
	@Autowired
	CommentRepository commentRepository;

	public Notification convertToDto(NotificationEntity entity) {
		Notification dto = new Notification();
		dto.setId(entity.getId());
		dto.setContent(entity.getContent());
		dto.setNotificationType(entity.getNotificationType());
		dto.setUserFrom(entity.getUserFrom());
		dto.setUserTo(entity.getUserTo());
		dto.setVideo(entity.getVideo() != null ? 				
				videoConverter.convertToDto(entity.getVideo(), entity.getUserTo().getId()): null);
		dto.setCreatedDate(new java.sql.Date(entity.getCreatedDate().getTime()));
		return dto;
	}
	
}
