package com.tictoc.user;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.tictoc.common.SecurityUtil;
import com.tictoc.emotion.EmotionRepository;
import com.tictoc.user.follow.FollowRepository;
import com.tictoc.video.VideoEntity;
import com.tictoc.video.VideoRepository;
import com.tictoc.video.service.VideoService;

@Component
public class UserConverter {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private FollowRepository followRepository;
	@Autowired
	VideoRepository videoRepository;
	@Autowired
	EmotionRepository emotionRepository;
	@Autowired
	VideoService videoService;

	public UserDTO convertToDto(UserEntity user) {
		UserDTO userDto = new UserDTO();
		Long idCurrent = SecurityUtil.getPrincipal();
		userDto.setId(user.getId());
		userDto.setUserName(user.getUserName());
		userDto.setName(user.getName());
		userDto.setAvatar(user.getAvatar());
		userDto.setEmail(user.getEmail());
		userDto.setBio(user.getBio());
		userDto.setFollowersCount(followRepository.countByUser(user));
		userDto.setFollowed(followRepository.findByUserAndFollowing(user, idCurrent).isPresent());
		userDto.setFacebookUrl(user.getFacebookUrl());
		userDto.setFollowingsCount(followRepository.countByFollowing(user.getId()));
		userDto.setLikesCount(totalLike(user));
		userDto.setBirthDay(user.getBirthDay());
		userDto.setPopularVideo(videoService.findPopularVideo(user.getId()));
		return userDto;
	}

	public UserEntity convertToEntity(UserDTO userDto) {
		UserEntity user = new UserEntity();
		user.setUserName(userDto.getUserName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setEmail(userDto.getEmail());
		user.setBio(userDto.getBio());
		user.setAvatar(userDto.getAvatar());
		user.setName(userDto.getName());
		user.setAvatar(userDto.getAvatar());
		user.setBirthDay(userDto.getBirthDay());
		return user;
	}

	public UserEntity convertFieldToEntity(Map<String, Object> fields, UserEntity userEntity) {
		fields.forEach((key, value) -> {
			long cc = 0;
			Field field = ReflectionUtils.findField(UserEntity.class, key);
			field.setAccessible(true);
			if (field.getName().equals("password")) {
				ReflectionUtils.setField(field, userEntity,
						passwordEncoder.encode((CharSequence) String.valueOf(value)));
			} else {
				if (value instanceof Integer) {
					cc = ((Number) value).longValue();
					ReflectionUtils.setField(field, userEntity, cc);
				} else {
					ReflectionUtils.setField(field, userEntity, value);
				}
			}
		});
		return userEntity;
	}

	private long totalLike(UserEntity user) {
		List<VideoEntity> videoEntities = videoRepository.findByCreatedBy(user.getId());
		long totalLike = 0;
		for (VideoEntity videoEntity : videoEntities) {
			totalLike += emotionRepository.countByVideo(videoEntity);
		}
		return totalLike;
	}

}
