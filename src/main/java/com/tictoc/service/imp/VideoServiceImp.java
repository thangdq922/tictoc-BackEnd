package com.tictoc.service.imp;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tictoc.dto.VideoDTO;
import com.tictoc.service.VideoService;

public class VideoServiceImp implements VideoService {

	@Override
	public VideoDTO saveVideo(VideoDTO videoDTO, MultipartFile upFile, MultipartFile upAvatar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VideoDTO findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VideoDTO> searchVdieo(String keyWord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VideoDTO> findAllVideo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VideoDTO> findVideoByUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVideo(long[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setView(VideoDTO dto) {
		// TODO Auto-generated method stub
		
	}

}
