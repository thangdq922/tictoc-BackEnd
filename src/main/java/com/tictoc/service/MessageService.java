package com.tictoc.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tictoc.dto.MessageDTO;
import com.tictoc.entity.MessageEnitty;
import com.tictoc.entity.UserEntity;

public interface MessageService {

	MessageDTO sendMessage(String content, String username, UserEntity userCurrent);

	Page<MessageEnitty> getMessageByUserFrom(String username);

	List<MessageDTO> getChatRoom(String username, Long currentID);

	void saveStatus(Long userToId);

	void clear(String userName);
}
