package com.tictoc.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tictoc.converter.NotificationConverter;
import com.tictoc.dto.Notification;
import com.tictoc.entity.NotificationEntity;
import com.tictoc.entity.UserEntity;
import com.tictoc.repository.NotificationRepository;
import com.tictoc.repository.UserRepository;
import com.tictoc.service.NotificationService;
import com.tictoc.util.SecurityUtil;

import jakarta.transaction.Transactional;

@Service
public class NotificationServiceImp implements NotificationService {
	@Autowired
	NotificationConverter converter;
	@Autowired
	NotificationRepository repository;
	@Autowired
	UserRepository userRepository;

	@Override
	public Notification createNotification(NotificationEntity notification) {
		return converter.convertToDto(repository.save(notification));
	}

	@Override
	public List<Notification> getNotificationsByUserTo(String userName) {
		UserEntity user = userRepository.findByUserName(userName).get();
		List<NotificationEntity> entities = repository.findByUserTo(user);
		List<Notification> notifications = entities.stream().map((entity) -> converter.convertToDto(entity))
				.collect(Collectors.toList());
		if (notifications.isEmpty() == false) {
			notifications.get(0)
					.setCountNotRead(repository.countByStatusIsFalseAndUserTo(notifications.get(0).getUserTo()));
		}
		return notifications;
	}

	@Override
	@Transactional
	public void clear() {
		repository.deleteAll(repository.findByUserTo(SecurityUtil.getUserCurrent()));
	}

	@Override
	@Transactional
	public void saveStatus() {
		List<NotificationEntity> entities = repository.findByUserTo(SecurityUtil.getUserCurrent());
		entities.forEach(entity -> entity.setStatus(true));
		repository.saveAll(entities);
	}
}
