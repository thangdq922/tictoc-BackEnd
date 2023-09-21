package com.tictoc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tictoc.constraint.NotificationType;
import com.tictoc.converter.VideoConverter;
import com.tictoc.dto.VideoDTO;
import com.tictoc.entity.EmotionEntity;
import com.tictoc.entity.FollowEntity;
import com.tictoc.entity.NotificationEntity;
import com.tictoc.entity.VideoEntity;
import com.tictoc.repository.EmotionRepository;
import com.tictoc.repository.FollowRepository;
import com.tictoc.repository.NotificationRepository;
import com.tictoc.repository.UserRepository;
import com.tictoc.repository.VideoRepository;
import com.tictoc.service.VideoService;
import com.tictoc.util.SecurityUtil;

import jakarta.transaction.Transactional;

@Service
public class VideoServiceImp implements VideoService {

	@Autowired
	VideoConverter converter;
	@Autowired
	VideoRepository videoRepository;
	@Autowired
	EmotionRepository emotionRepository;
	@Autowired
	FollowRepository followRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	NotificationRepository notificationRepository;

	@Override
	@Transactional
	public VideoDTO saveVideo(VideoDTO videoDTO) {
		VideoEntity videoEntity = new VideoEntity();
		videoEntity = converter.convertToEntity(videoDTO);

		return converter.convertToDto(videoRepository.save(videoEntity), SecurityUtil.getCurrentID());
	}

	@Override
	public VideoDTO findById(Long id) {
		Optional<VideoEntity> entity = videoRepository.findById(id);
		return converter.convertToDto(entity.get(), SecurityUtil.getCurrentID());
	}

	@Override
	public List<VideoDTO> searchVdieo(String keyWord, Pageable pageable) {
		Page<VideoEntity> entities = videoRepository.findByCaptionContaining(keyWord, pageable);
		return entities.stream().map((entity) -> converter.convertToDto(entity, SecurityUtil.getCurrentID()))
				.collect(Collectors.toList());
	}

	@Override
	public List<VideoDTO> findListVideo(String type, Pageable pageable) {
		if (type.equals("for-you")) {
			Page<VideoEntity> entities = videoRepository.findAll(pageable);
			return entities.stream().map((entity) -> converter.convertToDto(entity, SecurityUtil.getCurrentID()))
					.collect(Collectors.toList());
		} else {
			Long idCurrent = SecurityUtil.getCurrentID();
			List<FollowEntity> followEntities = followRepository.findByFollowing(idCurrent);
			List<VideoEntity> videoEntities = new ArrayList<>();
			followEntities.forEach(
					(entity) -> videoEntities.addAll(videoRepository.findByCreatedBy(entity.getUser().getId())));
			int start = (int) pageable.getOffset();
			int end = Math.min((start + pageable.getPageSize()), videoEntities.size());
			Page<VideoEntity> entities = new PageImpl<>(videoEntities.subList(start, end), pageable,
					videoEntities.size());
			return entities.stream().map((entity) -> converter.convertToDto(entity, idCurrent))
					.collect(Collectors.toList());
		}
	}

	@Override
	public List<VideoDTO> findVideoByUser(Long id) {
		List<VideoEntity> entities = videoRepository.findByCreatedBy(id);
		return entities.stream().map((entity) -> converter.convertToDto(entity, SecurityUtil.getCurrentID()))
				.collect(Collectors.toList());
	}

	@Override
	public VideoDTO findPopularVideo(Long id) {
		Optional<VideoEntity> entity = videoRepository.findTopByCreatedByOrderByViewsCountDesc(id);
		if (entity.isPresent()) {
			return converter.convertToDto(entity.get(), SecurityUtil.getCurrentID());
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteVideo(Long id) {
		videoRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void setView(Long id) {
		VideoEntity oldVideo = videoRepository.findById(id).get();
		oldVideo.setViewsCount(oldVideo.getViewsCount() + 1);
		videoRepository.save(oldVideo);
	}

	@Override
	@Transactional
	public VideoDTO like(Long id) {
		VideoEntity videoEntity = videoRepository.findById(id).get();
		EmotionEntity emotionEntity = new EmotionEntity();
		emotionEntity.setVideo(videoEntity);
		emotionRepository.save(emotionEntity);
		NotificationEntity notificationEntity = new NotificationEntity(
				SecurityUtil.getUserCurrent().getUserName() + " Liked Your Video",
				userRepository.findById(videoEntity.getCreatedBy()).get(), SecurityUtil.getUserCurrent(), videoEntity,
				NotificationType.LIKE);
		notificationRepository.save(notificationEntity);
		return converter.convertToDto(videoEntity, SecurityUtil.getCurrentID());
	}

	@Override
	@Transactional
	public VideoDTO unlike(Long id) {
		VideoEntity videoEntity = videoRepository.findById(id).get();
		Long idCurrent = SecurityUtil.getCurrentID();
		EmotionEntity emotionEntity = emotionRepository.findByVideoAndUser(videoEntity, idCurrent).get();
		emotionRepository.delete(emotionEntity);
		return converter.convertToDto(videoEntity, idCurrent);
	}

}
