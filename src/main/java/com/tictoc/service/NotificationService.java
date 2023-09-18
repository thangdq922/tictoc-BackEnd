package com.tictoc.service;

import java.util.List;

import com.tictoc.dto.Notification;
import com.tictoc.entity.NotificationEntity;

public interface NotificationService {

	Notification createNotification(NotificationEntity notification);

	List<Notification> getNotificationsByUserTo(String userName);

	void saveStatus();

	void clear();

}
