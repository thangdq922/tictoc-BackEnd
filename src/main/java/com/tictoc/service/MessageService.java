package com.tictoc.service;

import org.springframework.data.domain.Page;

import com.tictoc.dto.MessageDTO;
import com.tictoc.entity.MessageEnitty;

public interface MessageService {

	MessageDTO sendMessage(String content, String username);

	Page<MessageEnitty> getMessageByUserFrom(String username);
	
	Page<MessageEnitty> getMessageUserFromUserTo(String username);

	void saveStatus(Long userToId);

	void clear(String userName);
}
