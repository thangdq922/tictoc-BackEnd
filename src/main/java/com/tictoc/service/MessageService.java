package com.tictoc.service;

import java.util.List;

import com.tictoc.dto.MessageDTO;

public interface MessageService {

	MessageDTO sendMessage(String content, String username);

	List<MessageDTO> getMessageByUserFrom(String username);

	void saveStatus(Long userToId);

	void clear(String userName);
}
