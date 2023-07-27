package com.tictoc.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tictoc.comment.service.CommentService;

@CrossOrigin(origins = "http://localhost:5000")
@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	CommentService commentService;

	@PostMapping("/videos/{id}/comments")
	public ResponseEntity<CommentDTO> comment(@RequestBody String comment, @PathVariable Long id) {
		return new ResponseEntity<>(commentService.saveComment(comment, id), HttpStatus.OK);
	}

	@GetMapping("/videos/{id}/comments")
	public ResponseEntity<?> comment(@PathVariable Long id) {
		return new ResponseEntity<>(commentService.findByVideo(id), HttpStatus.OK);
	}

}
