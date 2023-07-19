package com.tictoc.service;

import com.tictoc.dto.EmotionDTO;
import com.tictoc.entity.VideoEntity;

public interface EmotionService {
	long countEmotion(VideoEntity video);
	
	void setEmotion(EmotionDTO dto);
	
	void deleteEmotion(EmotionDTO dto);
	
}
