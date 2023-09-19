package com.tictoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tictoc.dto.MessageDTO;
import com.tictoc.repository.MessageRepository;
import com.tictoc.service.MessageService;

@RestController
public class MessageController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	private MessageService messageService;
	@Autowired
	MessageRepository repository;

	@MessageMapping("/messages")
	public void getMessageByUserFrom(@Payload(required = false) String userName) {
		if (userName == null) {
			return;
		}
		List<MessageDTO> messages = messageService.getMessageByUserFrom(userName);
		simpMessagingTemplate.convertAndSendToUser(userName, "/queue/messages", messages);
	}

	@MessageMapping("/messages.sendMessage")
	public void sendMessage(@Header String userName, @Payload String Content) {
		MessageDTO message = messageService.sendMessage(Content, userName);
		simpMessagingTemplate.convertAndSendToUser(userName, "/queue/message", message);
	}

	@MessageMapping("/messages.clear")
	public void clearMessage(@Payload String userName) {
//		messageService.clear(userName);
		List<MessageDTO> messages = messageService.getMessageByUserFrom(userName);
		simpMessagingTemplate.convertAndSendToUser(userName, "/queue/messages", messages);
	}
	
	@GetMapping("/api/mess")
	public ResponseEntity<?> getSuggestedUser() {
		
		return new ResponseEntity<>(repository.bb(), HttpStatus.OK);}
	}
