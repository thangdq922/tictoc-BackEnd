package com.tictoc.service.impl;

import java.util.ArrayList;
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
	VideoService videoService;

	@Override
	@Transactional
	public UserDTO saveUser(UserDTO userDto) {
		RoleEntity role = roleRepository.findByCode("ROLE_USER");
		UserEntity user = new UserEntity();

		user = converter.convertToEntity(userDto);
		user.setRoles(Arrays.asList(role));

		return converter.convertToDto(userRepository.save(user), SecurityUtil.getPrincipalId());
	}

	@Override
	@Transactional
	public UserDTO saveFieldUser(Map<String, Object> fields) {
		Optional<UserEntity> existingUser = userRepository.findById(SecurityUtil.getPrincipalId());
		UserEntity userEntity = existingUser.get();
		if (existingUser.isPresent()) {
			userRepository.save(converter.convertFieldToEntity(fields, userEntity));
			return converter.convertToDto(userEntity, SecurityUtil.getPrincipalId());
		}
		return null;
	}

	@Override
	public UserDTO findById(Long id) {
		Optional<UserEntity> entity = userRepository.findById(id);
		return converter.convertToDto(entity.get(), SecurityUtil.getPrincipalId());
	}

	@Override
	public UserDTO findByUserName(String username) {
		UserEntity entity = userRepository.findByUserName(username).orElse(null);
		if (entity != null) {
			UserDTO userDTO = converter.convertToDto(entity, SecurityUtil.getPrincipalId());
			userDTO.setVideos(videoService.findVideoByUser(entity.getId()));
			return userDTO;
		}
		return null;
	}

	@Override
	public UserDTO findByUserNameOrEmail(String username, String email) {
		UserEntity entity = userRepository.findByUserNameOrEmail(username, email).orElse(null);
		if (entity != null) {
			UserDTO userDTO = converter.convertToDto(entity, SecurityUtil.getPrincipalId());
			userDTO.setVideos(videoService.findVideoByUser(entity.getId()));
			return userDTO;
		}
		return null;
	}

	@Override
	public List<UserDTO> findAllUsers(Pageable pageable) {
		Page<UserEntity> users = userRepository.findAll(pageable);
		List<UserDTO> dtos = users.stream().map((user) -> converter.convertToDto(user, SecurityUtil.getPrincipalId()))
				.collect(Collectors.toList());
		dtos.forEach((user) -> user.setPopularVideo(videoService.findPopularVideo(user.getId())));
		return dtos;
	}

	@Override
	public List<UserDTO> findUserFollowing(Pageable pageable) {
		Long idCurrent = SecurityUtil.getPrincipalId();
		List<FollowEntity> followEntities = followRepository.findByFollowing(idCurrent);
		List<UserEntity> userEntities = new ArrayList<>();
		followEntities.forEach((entity) -> userEntities.add(entity.getUser()));
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), userEntities.size());
		Page<UserEntity> entities = new PageImpl<>(userEntities.subList(start, end), pageable, userEntities.size());
		return entities.stream().map((entity) -> converter.convertToDto(entity, idCurrent))
				.collect(Collectors.toList());
	}

	@Override
	public List<UserDTO> findSuggestedUsers(Pageable pageable) {
		Long idCurrent = SecurityUtil.getPrincipalId();
		List<UserEntity> users = userRepository.findAll();
		List<UserDTO> dtos = users.stream().map((user) -> converter.convertToDto(user, idCurrent))
				.collect(Collectors.toList());
		dtos.removeIf(dto -> (dto.isFollowed() == true || dto.getId() == idCurrent));
		dtos.forEach((user) -> user.setPopularVideo(videoService.findPopularVideo(user.getId())));
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), dtos.size());
		Page<UserDTO> userDtos = new PageImpl<>(dtos.subList(start, end), pageable, dtos.size());
		return userDtos.getContent();
	}

	@Override
	@Transactional
	public void deleteUsers(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<UserDTO> searchUser(String search_key, Pageable pageable) {
		Page<UserEntity> entities = userRepository.findByUserNameContaining(search_key, pageable);
		return entities.stream().map((entity) -> converter.convertToDto(entity, SecurityUtil.getPrincipalId()))
				.collect(Collectors.toList());
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
		return converter.convertToDto(userEntity, SecurityUtil.getPrincipalId());
	}

	@Override
	@Transactional
	public UserDTO unfollow(Long id) {
		UserEntity userEntity = userRepository.findById(id).get();
		Long idCurrent = SecurityUtil.getPrincipalId();
		FollowEntity followEntity = followRepository.findByUserAndFollowing(userEntity, idCurrent).get();
		followRepository.delete(followEntity);
		return converter.convertToDto(userEntity, idCurrent);
	}

}