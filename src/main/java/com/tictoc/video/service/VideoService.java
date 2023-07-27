package com.tictoc.video.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.tictoc.video.VideoDTO;

public interface VideoService {

	VideoDTO saveVideo(VideoDTO videoDTO, MultipartFile upFile);

	VideoDTO findById(Long id);

	List<VideoDTO> searchVdieo(String keyWord, Pageable pageable);

	List<VideoDTO> findAllVideo(String type, Pageable pageable);

	List<VideoDTO> findVideoByUser(Long id);
	
	VideoDTO findPopularVideo(Long id);

	void deleteVideo(long[] ids);

	void setView(VideoDTO dto);

	VideoDTO like(Long id);

	VideoDTO unlike(Long id);
}
