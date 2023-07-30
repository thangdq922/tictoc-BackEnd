package com.tictoc.video.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tictoc.common.FileService;
import com.tictoc.common.SecurityUtil;
import com.tictoc.emotion.EmotionEntity;
import com.tictoc.emotion.EmotionRepository;
import com.tictoc.user.follow.FollowEntity;
import com.tictoc.user.follow.FollowRepository;
import com.tictoc.video.VideoConverter;
import com.tictoc.video.VideoDTO;
import com.tictoc.video.VideoEntity;
import com.tictoc.video.VideoRepository;

import jakarta.transaction.Transactional;

@Service
public class VideoServiceImp implements VideoService {

	@Autowired
	VideoConverter converter;
	@Autowired
	private FileService fileService;
	@Autowired
	VideoRepository videoRepository;
	@Autowired
	EmotionRepository emotionRepository;
	@Autowired
	FollowRepository followRepository;

	@Override
	@Transactional
	public VideoDTO saveVideo(VideoDTO videoDTO, MultipartFile upFile) {
		VideoEntity videoEntity = new VideoEntity();
		videoEntity = converter.convertToEntity(videoDTO);
		if (upFile != null) {
			videoEntity.setFileUrl(fileService.handleSaveFile(upFile, videoDTO.getId(), "video"));
		}
		return converter.convertToDto(videoRepository.save(videoEntity));
	}

	@Override
	public VideoDTO findById(Long id) {
		Optional<VideoEntity> entity = videoRepository.findById(id);
		return converter.convertToDto(entity.get());
	}

	@Override
	public List<VideoDTO> searchVdieo(String keyWord, Pageable pageable) {
		Page<VideoEntity> entities = videoRepository.findByCaptionContaining(keyWord, pageable);
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	public List<VideoDTO> findListVideo(String type, Pageable pageable) {
		if (type.equals("for-you")) {
			Page<VideoEntity> entities = videoRepository.findAll(pageable);
			return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
		} else {
			Long idCurrent = SecurityUtil.getPrincipal();
			List<FollowEntity> followEntities = followRepository.findByFollowing(idCurrent);
			List<VideoEntity> videoEntities = new ArrayList<>();
			followEntities.forEach(
					(entity) -> videoEntities.addAll(videoRepository.findByCreatedBy(entity.getUser().getId())));
			int start = (int) pageable.getOffset();
			int end = Math.min((start + pageable.getPageSize()), videoEntities.size());
			Page<VideoEntity> entities = new PageImpl<>(videoEntities.subList(start, end), pageable,
					videoEntities.size());
			return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
		}
	}

	@Override
	public List<VideoDTO> findVideoByUser(Long id) {
		List<VideoEntity> entities = videoRepository.findByCreatedBy(id);
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	public VideoDTO findPopularVideo(Long id) {
		Optional<VideoEntity> entity = videoRepository.findTopByCreatedByOrderByViewsCountDesc(id);
		if (entity.isPresent()) {
			return converter.convertToDto(entity.get());
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
		return converter.convertToDto(videoEntity);
	}

	@Override
	@Transactional
	public VideoDTO unlike(Long id) {
		VideoEntity videoEntity = videoRepository.findById(id).get();
		Long idCurrent = SecurityUtil.getPrincipal();
		EmotionEntity emotionEntity = emotionRepository.findByVideoAndUser(videoEntity, idCurrent).get();
		emotionRepository.delete(emotionEntity);
		return converter.convertToDto(videoEntity);
	}

}
