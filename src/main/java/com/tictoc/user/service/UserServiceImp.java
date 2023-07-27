package com.tictoc.user.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tictoc.common.FileService;
import com.tictoc.common.SecurityUtil;
import com.tictoc.user.UserConverter;
import com.tictoc.user.UserDTO;
import com.tictoc.user.UserEntity;
import com.tictoc.user.UserRepository;
import com.tictoc.user.follow.FollowEntity;
import com.tictoc.user.follow.FollowRepository;
import com.tictoc.user.role.RoleEntity;
import com.tictoc.user.role.RoleRepository;
import com.tictoc.video.service.VideoService;

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
	private UserConverter converter;
	@Autowired
	private FileService fileService;
	@Autowired
	VideoService videoService;

	@Override
	@Transactional
	public UserDTO saveUser(UserDTO userDto, MultipartFile upAvatar) {
		RoleEntity role = roleRepository.findByCode("ROLE_USER");
		UserEntity user = new UserEntity();

		user = converter.convertToEntity(userDto);
		user.setRoles(Arrays.asList(role));
		if (upAvatar != null) {
			user.setAvatar(fileService.handleSaveFile(upAvatar, userDto.getId(), "user"));
		}
		return converter.convertToDto(userRepository.save(user));
	}

	@Override
	@Transactional
	public UserDTO saveFieldUser(Map<String, Object> fields, MultipartFile upAvatar) {
		Optional<UserEntity> existingUser = userRepository.findById(((Number) fields.get("id")).longValue());
		UserEntity userEntity = existingUser.get();
		if (existingUser.isPresent()) {
			userRepository.save(converter.convertFieldToEntity(fields, userEntity));
			if (upAvatar != null) {
				userEntity.setAvatar(fileService.handleSaveFile(upAvatar, userEntity.getId(), "user"));
			}
			return converter.convertToDto(userEntity);
		}
		return null;
	}

	@Override
	public UserDTO findById(Long id) {
		Optional<UserEntity> entity = userRepository.findById(id);
		return converter.convertToDto(entity.get());
	}

	@Override
	public UserDTO findByUserName(String username) {
		UserEntity entity = userRepository.findByUserName(username).get();
		UserDTO userDTO = converter.convertToDto(entity);
		userDTO.setVideos(videoService.findVideoByUser(entity.getId()));
		return userDTO;
	}

	@Override
	public List<UserDTO> findAllUsers(Pageable pageable) {
		Page<UserEntity> users = userRepository.findAll(pageable);
		return users.stream().map((user) -> converter.convertToDto(user)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteUsers(long[] ids) {
		for (long id : ids) {
			userRepository.deleteById(id);
		}
	}

	@Override
	public List<UserDTO> searchUser(String search_key, Pageable pageable) {
		Page<UserEntity> entities = userRepository.findByUserNameContaining(search_key, pageable);
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public UserDTO follow(Long id) {
		UserEntity userEntity = userRepository.findById(id).get();
		FollowEntity followEntity = new FollowEntity();
		followEntity.setUser(userEntity);
		followRepository.save(followEntity);
		return converter.convertToDto(userEntity);
	}

	@Override
	@Transactional
	public UserDTO unfollow(Long id) {
		UserEntity userEntity = userRepository.findById(id).get();
		Long idCurrent = SecurityUtil.getPrincipal();
		FollowEntity followEntity = followRepository.findByUserAndFollowing(userEntity, idCurrent).get();
		followRepository.delete(followEntity);
		return converter.convertToDto(userEntity);
	}

}
