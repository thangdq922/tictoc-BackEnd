package com.tictoc.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.tictoc.dto.VideoDTO;

public interface VideoService {

	VideoDTO saveVideo(VideoDTO videoDTO);
	
	VideoDTO editVideo(Map<String, Object> fields);

	VideoDTO findById(Long id);

	List<VideoDTO> searchVdieo(String keyWord, Pageable pageable);

	List<VideoDTO> findListVideo(String type, Pageable pageable);

	List<VideoDTO> findVideoByUser(Long id);
	
	VideoDTO findPopularVideo(Long id);

	void deleteVideo(Long id);

	void setView(Long id);

	VideoDTO like(Long id);

	VideoDTO unlike(Long id);
}
