package com.tictoc.service.imp;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tictoc.dto.UserDTO;
import com.tictoc.entity.UserEntity;
import com.tictoc.service.UserService;

public class UserServiceImp implements UserService {

	@Override
	public UserDTO saveUser(UserDTO userDto, MultipartFile upAvatar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity findByUserName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> findAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUsers(long[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserDTO> searchUser(String search_key) {
		// TODO Auto-generated method stub
		return null;
	}

}
