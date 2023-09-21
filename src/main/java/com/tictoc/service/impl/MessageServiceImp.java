package com.tictoc.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tictoc.converter.MessageConverter;
import com.tictoc.dto.MessageDTO;
import com.tictoc.entity.MessageEnitty;
import com.tictoc.entity.UserEntity;
import com.tictoc.repository.MessageRepository;
import com.tictoc.repository.UserRepository;
import com.tictoc.service.MessageService;
import com.tictoc.util.SecurityUtil;

import jakarta.transaction.Transactional;

@Service
public class MessageServiceImp implements MessageService {

	@Autowired
	MessageRepository repository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	MessageConverter converter;

	@Override
	@Transactional
	public MessageDTO sendMessage(String content, String username) {
		UserEntity userEntity = userRepository.findByUserName(username).orElse(null);
		MessageEnitty entity = new MessageEnitty();
		entity.setContent(content);
		entity.setUserTo(userEntity);
		entity.setRoom(getRoom(SecurityUtil.getPrincipalId(), userEntity.getId()));
		return converter.convertToDto(repository.save(entity));
	}

	@Override
	public Page<MessageEnitty> getMessageByUserFrom(String username) {
		UserEntity userEntity = userRepository.findByUserName(username).orElse(null);
		return repository.findMessageByUserFrom(userEntity.getId(),
				PageRequest.of(0, 1000, Sort.Direction.DESC, "createddate"));
	}

	@Override
	public List<MessageDTO> getChatRoom(String username) {
		UserEntity userEntity = userRepository.findByUserName(username).orElse(null);
		List<MessageEnitty> entities = repository
				.findByRoom(getRoom(SecurityUtil.getPrincipalId(), userEntity.getId()));
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void saveStatus(Long userToId) {
//		UserEntity userEntity = userRepository.findById(userToId).orElse(null);
//		List<MessageEnitty> entities = repository.findByUserFromAndUserTo(SecurityUtil.getUserCurrent(), userEntity);
//		entities.forEach(entity -> entity.setStatus(true));
//		repository.saveAll(entities);
	}

	@Override
	@Transactional
	public void clear(String userName) {
//		UserEntity userEntity = userRepository.findByUserName(userName).orElse(null);
//		repository.deleteAll(repository.findByUserFromAndUserTo(SecurityUtil.getUserCurrent(), userEntity));

	}

	private String getRoom(Long userId1, Long userId2) {
		return userId1 > userId2 ? userId2 + "-" + userId1 : userId1 + "-" + userId2;
	}

}
