package com.tictoc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tictoc.dto.MessageDTO;
import com.tictoc.entity.UserEntity;

public interface MessageService {

	MessageDTO sendMessage(String content, String username, UserEntity userCurrent);

	Page<MessageDTO> getMessageByUserFrom(String username);

	Page<MessageDTO> getChatRoom(String username, Long currentID, Pageable pageable);

	Page<MessageDTO> saveStatus(String username, Long currentID, Pageable pageable);

	void deleteMessage(Long id);
}
