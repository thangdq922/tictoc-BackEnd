package com.tictoc.video.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tictoc.common.FileService;
import com.tictoc.common.SecurityUtil;
import com.tictoc.emotion.EmotionEntity;
import com.tictoc.emotion.EmotionRepository;
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

	@Override
	@Transactional
	public VideoDTO saveVideo(VideoDTO videoDTO, MultipartFile upFile) {
		VideoEntity videoEntity = new VideoEntity();
		videoEntity = converter.convertToEntity(videoDTO);
		if (upFile != null) {
			videoEntity.setFileUrl(fileService.handleSaveFile(upFile, videoDTO.getId(), "video"));
		}
		return converter.convertToDto(videoEntity);
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
	public List<VideoDTO> findAllVideo(String type, Pageable pageable) {
		Page<VideoEntity> entities = videoRepository.findAll(pageable);
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
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
	public void deleteVideo(long[] ids) {
		for (long id : ids) {
			videoRepository.deleteById(id);
		}
	}

	@Override
	@Transactional
	public void setView(VideoDTO dto) {
		Optional<VideoEntity> oldVideo = videoRepository.findById(dto.getId());
		oldVideo.get().setViewsCount(dto.getViewsCount());
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
