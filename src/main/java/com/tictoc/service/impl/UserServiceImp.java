package com.tictoc.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tictoc.constraint.NotificationType;
import com.tictoc.converter.UserConverter;
import com.tictoc.dto.UserDTO;
import com.tictoc.entity.FollowEntity;
import com.tictoc.entity.NotificationEntity;
import com.tictoc.entity.RoleEntity;
import com.tictoc.entity.UserEntity;
import com.tictoc.repository.FollowRepository;
import com.tictoc.repository.NotificationRepository;
import com.tictoc.repository.RoleRepository;
import com.tictoc.repository.UserRepository;
import com.tictoc.service.UserService;
import com.tictoc.service.VideoService;
import com.tictoc.util.SecurityUtil;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private FollowRepository followRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private UserConverter converter;
	@Autowired
	private VideoService videoService;

	@Override
	@Transactional
	public UserDTO saveUser(UserDTO userDto) {
		RoleEntity role = roleRepository.findByCode("ROLE_USER");
		UserEntity user = new UserEntity();

		user = converter.convertToEntity(userDto);
		user.setRoles(Arrays.asList(role));
		return converter.convertToDto(userRepository.save(user), SecurityUtil.getCurrentID());
	}

	@Override
	@Transactional
	public UserDTO saveFieldUser(Map<String, Object> fields) {
		Optional<UserEntity> existingUser = userRepository.findById(SecurityUtil.getCurrentID());
		UserEntity userEntity = existingUser.get();
		if (existingUser.isPresent()) {
			userRepository.save(converter.convertFieldToEntity(fields, userEntity));
			return converter.convertToDto(userEntity, SecurityUtil.getCurrentID());
		}
		return null;
	}

	@Override
	public UserDTO findById(Long id) {
		Optional<UserEntity> entity = userRepository.findById(id);
		return converter.convertToDto(entity.get(), SecurityUtil.getCurrentID());
	}

	@Override
	public UserDTO findByUserName(String username) {
		UserEntity entity = userRepository.findByUserName(username).orElse(null);
		if (entity != null) {
			UserDTO userDTO = converter.convertToDto(entity, SecurityUtil.getCurrentID());
			userDTO.setVideos(videoService.findVideoByUser(entity.getId()));
			return userDTO;
		}
		return null;
	}

	@Override
	public UserDTO findByUserNameOrEmail(String username, String email) {
		UserEntity entity = userRepository.findByUserNameOrEmail(username, email).orElse(null);
		if (entity != null) {
			UserDTO userDTO = converter.convertToDto(entity, SecurityUtil.getCurrentID());
			userDTO.setVideos(videoService.findVideoByUser(entity.getId()));
			return userDTO;
		}
		return null;
	}

	@Override
	public List<UserDTO> findAllUsers(Pageable pageable) {
		Long idCurrent = SecurityUtil.getCurrentID();
		Page<UserEntity> users = userRepository.findAll(pageable);
		List<UserDTO> dtos = users.map(user -> converter.convertToDto(user, idCurrent)).getContent();
		dtos.forEach((user) -> user.setPopularVideo(videoService.findPopularVideo(user.getId())));
		return dtos;
	}

	@Override
	public List<UserDTO> findUserFollowing(Pageable pageable) {
		Long idCurrent = SecurityUtil.getCurrentID();
		Page<UserEntity> entities = userRepository.findByFollowing(idCurrent, pageable);
		return entities.map((entity) -> converter.convertToDto(entity, idCurrent)).getContent();
	}

	@Override
	public List<UserDTO> findSuggestedUsers(Pageable pageable) {
		Long idCurrent = SecurityUtil.getCurrentID();
		List<UserEntity> entities = idCurrent == null ? userRepository.findAll()
				: userRepository.findSuggestedUsers(idCurrent);
		
		List<UserDTO> dtos = entities.stream().map(entity -> converter.convertToDto(entity, idCurrent))
				.collect(Collectors.toList());
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), dtos.size());
		List<UserDTO> pageContent = dtos.subList(start, end);
		return new PageImpl<>(pageContent, pageable, dtos.size()).getContent();
	}

	@Override
	@Transactional
	public void deleteUsers(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<UserDTO> searchUser(String search_key, Pageable pageable) {
		Long idCurrent = SecurityUtil.getCurrentID();
		Page<UserEntity> entities = userRepository.findByUserNameContaining(search_key, pageable);
		return entities.map(entity -> converter.convertToDto(entity, idCurrent)).getContent();
	}

	@Override
	@Transactional
	public UserDTO follow(Long id) {
		UserEntity userEntity = userRepository.findById(id).get();
		FollowEntity followEntity = new FollowEntity();
		followEntity.setUser(userEntity);
		followRepository.save(followEntity);

		NotificationEntity notificationEntity = new NotificationEntity(
				SecurityUtil.getUserCurrent().getUserName() + " Followed you", userEntity,
				SecurityUtil.getUserCurrent(), null, NotificationType.FOLLOW);
		notificationRepository.save(notificationEntity);
		return converter.convertToDto(userEntity, SecurityUtil.getCurrentID());
	}

	@Override
	@Transactional
	public UserDTO unfollow(Long id) {
		UserEntity userEntity = userRepository.findById(id).get();
		Long idCurrent = SecurityUtil.getCurrentID();
		FollowEntity followEntity = followRepository.findByUserAndFollowing(userEntity, idCurrent).get();
		followRepository.delete(followEntity);
		return converter.convertToDto(userEntity, idCurrent);
	}

}
