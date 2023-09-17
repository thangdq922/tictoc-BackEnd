package com.tictoc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tictoc.dto.UserDTO;
import com.tictoc.service.UserService;

@RestController
@RequestMapping("/api/")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("users/suggested")
	public ResponseEntity<?> getSuggestedUser(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int per_page) {
		Pageable pageSlice = PageRequest.of(page - 1, per_page, Sort.by("followersCount").descending());
		return new ResponseEntity<>(userService.findSuggestedUsers(pageSlice), HttpStatus.OK);
	}

	@GetMapping(value = "users/@{username}")
	public UserDTO findContact(@PathVariable String username) {
		UserDTO userDto = userService.findByUserName(username);
		if (userDto == null) {
			ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		return userDto;
	}

	@GetMapping("me/followings")
	public ResponseEntity<List<UserDTO>> getUserFollow(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int per_page) {
		Pageable pageSlice = PageRequest.of(page - 1, per_page);
		return new ResponseEntity<>(userService.findUserFollowing(pageSlice), HttpStatus.OK);
	}

	@GetMapping("users/search")
	public ResponseEntity<List<UserDTO>> searchUser(@RequestParam String type, @RequestParam String q) {
		Pageable pageSlice = type == "less" ? PageRequest.of(0, 5) : PageRequest.of(0, 10);
		return new ResponseEntity<>(userService.searchUser(q, pageSlice), HttpStatus.OK);
	}

	@PatchMapping(value = "me")
	public ResponseEntity<?> updateField(@RequestBody Map<String, Object> user) {
		return ResponseEntity.ok(userService.saveFieldUser(user));
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.deleteUsers(id);
		return ResponseEntity.ok("Delete Success");
	}

	@PostMapping("users/{id}/follow")
	public ResponseEntity<UserDTO> follow(@PathVariable Long id) {
		return ResponseEntity.ok(userService.follow(id));
	}

	@PostMapping("users/{id}/unfollow")
	public ResponseEntity<UserDTO> unfollow(@PathVariable Long id) {
		return ResponseEntity.ok(userService.unfollow(id));
	}
}
