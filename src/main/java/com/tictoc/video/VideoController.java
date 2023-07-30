package com.tictoc.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tictoc.video.service.VideoService;

@CrossOrigin(origins = "http://localhost:5000")
@RestController
@RequestMapping("/api/videos")
public class VideoController {

	@Autowired
	VideoService videoService;

	@PostMapping
	public ResponseEntity<?> postVideo(@RequestPart VideoDTO video,
			@RequestPart(required = false) MultipartFile upFile) {

		return new ResponseEntity<>(videoService.saveVideo(video, upFile), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getListVideo(@RequestParam String type, @RequestParam int page) {
		Pageable pageSlice = PageRequest.of(page - 1, 15, Sort.by("createdDate").descending());
		return new ResponseEntity<>(videoService.findListVideo(type, pageSlice), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getVideo(@PathVariable Long id) {

		return new ResponseEntity<>(videoService.findById(id), HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchVideo(@RequestParam("type") String type, @RequestParam("q") String q) {
		Pageable pageSlice = type == "less" ? PageRequest.of(0, 5) : PageRequest.of(0, 15);
		return new ResponseEntity<>(videoService.searchVdieo(q, pageSlice), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVideo(@PathVariable Long id) {
		videoService.deleteVideo(id);
		return ResponseEntity.ok("Delete Success");
	}

	@PatchMapping("/{id}/view")
	public void setView(@PathVariable Long id) {
		videoService.setView(id);
	}

	@PostMapping("/{id}/like")
	public ResponseEntity<?> like(@PathVariable("id") Long id) {
		return ResponseEntity.ok(videoService.like(id));
	}

	@PostMapping("/{id}/unlike")
	public ResponseEntity<?> unlike(@PathVariable("id") Long id) {
		return ResponseEntity.ok(videoService.unlike(id));
	}

}
