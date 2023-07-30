package com.tictoc.comment.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tictoc.comment.CommentConverter;
import com.tictoc.comment.CommentDTO;
import com.tictoc.comment.CommentEntity;
import com.tictoc.comment.CommentRepository;
import com.tictoc.video.VideoEntity;
import com.tictoc.video.VideoRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentServiceImp implements CommentService {

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	VideoRepository videoRepository;
	@Autowired
	CommentConverter converter;

	@Override
	@Transactional
	public CommentDTO saveComment(String comment, Long id) {
		VideoEntity videoEntity = videoRepository.findById(id).get();
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setVideo(videoEntity);
		commentEntity.setComments(comment);
		commentRepository.save(commentEntity);
		return converter.convertToDto(commentEntity);
	}

	@Override
	public List<CommentDTO> findByComment(String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDTO> findAllComments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDTO> findByVideo(Long id) {
		Optional<VideoEntity> video = videoRepository.findById(id);
		List<CommentEntity> entities = commentRepository.findByVideo(video.get());
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteComment(Long id) {
		commentRepository.deleteById(id);
	}

}
