package com.tictoc.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
		return converter.convertToDto(repository.save(entity));
	}

	@Override
	public List<MessageDTO> getMessageByUserFrom(String username) {
		UserEntity userEntity = userRepository.findByUserName(username).orElse(null);
		List<MessageEnitty> entities = repository.findMessageByUserFrom(userEntity.getId());
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void saveStatus(Long userToId) {
		UserEntity userEntity = userRepository.findById(userToId).orElse(null);
		List<MessageEnitty> entities = repository.findByUserFromAndUserTo(SecurityUtil.getUserCurrent(), userEntity);
		entities.forEach(entity -> entity.setStatus(true));
		repository.saveAll(entities);
	}

	@Override
	@Transactional
	public void clear(String userName) {
		UserEntity userEntity = userRepository.findByUserName(userName).orElse(null);
		repository.deleteAll(repository.findByUserFromAndUserTo(SecurityUtil.getUserCurrent(), userEntity));

	}

}
