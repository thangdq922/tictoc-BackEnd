package com.tictoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tictoc.dto.MessageDTO;
import com.tictoc.entity.UserEntity;
import com.tictoc.service.MessageService;
import com.tictoc.util.SecurityUtil;

@RestController
public class MessageController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	private MessageService messageService;

	@MessageMapping("/messages")
	public void getMessageByUserFrom(@Payload(required = false) String userName) {
		if (userName == null) {
			return;
		}
		simpMessagingTemplate.convertAndSendToUser(userName, "/queue/messages",
				messageService.getMessageByUserFrom(userName));
	}

	@MessageMapping("/messages.chatroom")
	public void getSuggestedUser(@Payload String userNameTo, UsernamePasswordAuthenticationToken u) {
		Long idCurrent = SecurityUtil.getPrincipal(u).getId();
		Pageable pageSlice = PageRequest.of(0, 15);
		Page<MessageDTO> messRoom = messageService.getChatRoom(userNameTo, idCurrent, pageSlice);
		simpMessagingTemplate.convertAndSendToUser(u.getName(), "/queue/chatroom", messRoom);
	}

	@MessageMapping("/messages.sendMessage")
	public void sendMessage(@Header String userNameTo, @Payload String Content, UsernamePasswordAuthenticationToken u) {
		UserEntity userCurrent = SecurityUtil.getPrincipal(u);
		messageService.sendMessage(Content, userNameTo, userCurrent);

		Pageable pageSlice = PageRequest.of(0, 15);
		Page<MessageDTO> messRoom = messageService.getChatRoom(userNameTo, userCurrent.getId(), pageSlice);
		simpMessagingTemplate.convertAndSendToUser(userNameTo, "/queue/chatroom", messRoom);
		simpMessagingTemplate.convertAndSendToUser(u.getName(), "/queue/chatroom", messRoom);

		simpMessagingTemplate.convertAndSendToUser(userNameTo, "/queue/messages",
				messageService.getMessageByUserFrom(userNameTo));
		simpMessagingTemplate.convertAndSendToUser(u.getName(), "/queue/messages",
				messageService.getMessageByUserFrom(u.getName()));
	}

	@MessageMapping("/messages.setStatus")
	public void saveStatus(@Payload String userNameTo, UsernamePasswordAuthenticationToken u) {
		Long idCurrent = SecurityUtil.getPrincipal(u).getId();
		Pageable pageSlice = PageRequest.of(0, 15);
		Page<MessageDTO> messRoom = messageService.saveStatus(userNameTo, idCurrent, pageSlice);
		
		simpMessagingTemplate.convertAndSendToUser(userNameTo, "/queue/chatroom", messRoom);
		simpMessagingTemplate.convertAndSendToUser(u.getName(), "/queue/messages",
				messageService.getMessageByUserFrom(u.getName()));
	}

	@MessageMapping("/messages.delete")
	public void deleteMessage(@Payload MessageDTO mess, UsernamePasswordAuthenticationToken u) {
		Long idCurrent = SecurityUtil.getPrincipal(u).getId();
		String userTo = mess.getUserTo().getUserName();
		
		if (mess.getUserFrom().getId() == idCurrent) {
			messageService.deleteMessage(mess.getId());
			Pageable pageSlice = PageRequest.of(0, 15);
			Page<MessageDTO> messRoom = messageService.getChatRoom(userTo, idCurrent, pageSlice);
			simpMessagingTemplate.convertAndSendToUser(userTo, "/queue/chatroom", messRoom);
			simpMessagingTemplate.convertAndSendToUser(mess.getUserFrom().getUserName(), "/queue/chatroom", messRoom);
		}
	}

	@GetMapping("/api/mess/{userNameTo}/chatroom")
	public ResponseEntity<?> getChatroom(@PathVariable String userNameTo, @RequestParam(defaultValue = "1") int page) {
		Pageable pageSlice = PageRequest.of(page - 1, 15);
		Page<MessageDTO> messRoom = messageService.getChatRoom(userNameTo, SecurityUtil.getCurrentID(), pageSlice);
		return new ResponseEntity<>(messRoom, HttpStatus.OK);
	}
}
