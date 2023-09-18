package com.tictoc.converter;

import org.springframework.stereotype.Component;

import com.tictoc.dto.MessageDTO;
import com.tictoc.entity.MessageEnitty;

@Component
public class MessageConverter {

	public MessageDTO convertToDto(MessageEnitty entity) {
		MessageDTO dto = new MessageDTO();
		dto.setId(entity.getId());
		dto.setContent(entity.getContent());
		dto.setUserFrom(entity.getUserFrom());
		dto.setUserTo(entity.getUserTo());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setStatus(entity.isStatus());
		return dto;
	}
}
