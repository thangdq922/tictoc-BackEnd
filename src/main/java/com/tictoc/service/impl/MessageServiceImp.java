package com.tictoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tictoc.converter.MessageConverter;
import com.tictoc.dto.MessageDTO;
import com.tictoc.entity.MessageEnitty;
import com.tictoc.entity.UserEntity;
import com.tictoc.repository.MessageRepository;
import com.tictoc.repository.UserRepository;
import com.tictoc.service.MessageService;

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
	public MessageDTO sendMessage(String content, String username, UserEntity userCurrent) {
		UserEntity userEntity = userRepository.findByUserName(username).orElse(null);
		MessageEnitty entity = new MessageEnitty();
		entity.setContent(content);
		entity.setUserTo(userEntity);
		entity.setUserFrom(userCurrent);
		entity.setRoom(getRoom(userCurrent.getId(), userEntity.getId()));
		return converter.convertToDto(repository.save(entity));
	}

	@Override
	public Page<MessageDTO> getMessageByUserFrom(String username) {
		UserEntity userEntity = userRepository.findByUserName(username).orElse(null);
		Pageable pageSlice = PageRequest.of(0, 100, Sort.Direction.DESC, "createddate");
		Page<MessageEnitty> entities =  repository.findMessageByUserFrom(userEntity.getId(),pageSlice);
		Page<MessageDTO> dtos = entities.map(entity -> converter.convertToDto(entity));
		if (dtos.getContent().isEmpty() == false) {
			dtos.getContent().get(0).setCountNotRead(repository.countStatusMessage(userEntity.getId()));
		}
		return dtos;
	}

	@Override
	public Page<MessageDTO> getChatRoom(String username, Long currentId, Pageable pageable) {
		UserEntity userEntity = userRepository.findByUserName(username).orElse(null);
		Page<MessageEnitty> entities = 
				repository.findByRoomOrderByCreatedDateDesc(getRoom(currentId, userEntity.getId()), pageable);
		return entities.map(entity -> converter.convertToDto(entity));
	}

	@Override
	@Transactional
	public  Page<MessageDTO> saveStatus(String username, Long currentId, Pageable pageable) {
		UserEntity userEntity = userRepository.findByUserName(username).orElse(null);
		Page<MessageEnitty> entities = 
				repository.findByRoomOrderByCreatedDateDesc(getRoom(currentId, userEntity.getId()),pageable);
		entities.forEach(entity -> entity.setStatus(true));
		repository.saveAll(entities);
		return entities.map(entity -> converter.convertToDto(entity));
	}

	@Override
	@Transactional
	public void deleteMessage(Long id) {
		repository.deleteById(id);
	}

	private String getRoom(Long userId1, Long userId2) {
		return userId1 > userId2 ? userId2 + "-" + userId1 : userId1 + "-" + userId2;
	}

}
