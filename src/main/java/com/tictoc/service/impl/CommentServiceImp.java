package com.tictoc.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tictoc.constraint.NotificationType;
import com.tictoc.converter.CommentConverter;
import com.tictoc.dto.CommentDTO;
import com.tictoc.entity.CommentEntity;
import com.tictoc.entity.NotificationEntity;
import com.tictoc.entity.VideoEntity;
import com.tictoc.repository.CommentRepository;
import com.tictoc.repository.NotificationRepository;
import com.tictoc.repository.UserRepository;
import com.tictoc.repository.VideoRepository;
import com.tictoc.service.CommentService;
import com.tictoc.util.SecurityUtil;

import jakarta.transaction.Transactional;

@Service
public class CommentServiceImp implements CommentService {

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	VideoRepository videoRepository;
	@Autowired
	CommentConverter converter;
	@Autowired
	UserRepository userRepository;
	@Autowired
	NotificationRepository notificationRepository;

	@Override
	@Transactional
	public CommentDTO saveComment(String comment, Long id) {
		VideoEntity videoEntity = videoRepository.findById(id).get();
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setVideo(videoEntity);
		commentEntity.setComments(comment);
		commentRepository.save(commentEntity);

		if (SecurityUtil.getUserCurrent().getId() != videoEntity.getCreatedBy()) {
			NotificationEntity notificationEntity = new NotificationEntity(
					SecurityUtil.getUserCurrent().getUserName() + " Commented Your Video",
					userRepository.findById(videoEntity.getCreatedBy()).get(), 
					SecurityUtil.getUserCurrent(),
					videoEntity, NotificationType.COMMENT);
			notificationRepository.save(notificationEntity);
		}
		return converter.convertToDto(commentEntity);
	}

	@Override
	public List<CommentDTO> findByComment(String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDTO> findAllComments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDTO> findByVideo(Long id) {
		Optional<VideoEntity> video = videoRepository.findById(id);
		List<CommentEntity> entities = commentRepository.findByVideo(video.get());
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteComment(Long id) {
		commentRepository.deleteById(id);
	}

}
