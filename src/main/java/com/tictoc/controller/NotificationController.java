package com.tictoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tictoc.dto.Notification;
import com.tictoc.service.NotificationService;

@RestController
public class NotificationController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	NotificationService notificationService;

	@MessageMapping("/notification")
	public void getNotificationsByUserTo(@Payload(required = false) String userName) {
		if (userName == null) {
			return;
		}
		List<Notification> notifs = notificationService.getNotificationsByUserTo(userName);
		simpMessagingTemplate.convertAndSendToUser(userName, "/queue/notif", notifs);
	}

	@PatchMapping("/api/notifications")
	public void setStatus() {
		notificationService.saveStatus();
	}

	@DeleteMapping("/api/notifications")
	public ResponseEntity<?> deleteNotif() {
		notificationService.clear();
		return ResponseEntity.ok("Delete Success");
	}
}
