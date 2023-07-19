package com.tictoc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tictoc.dto.UserDTO;
import com.tictoc.entity.UserEntity;

public interface UserService {
    UserDTO saveUser(UserDTO userDto, MultipartFile upAvatar);
    
    UserDTO findById(Long id);

    UserEntity findByUserName(String username);

    List<UserDTO> findAllUsers();
    
    void deleteUsers(long[] ids);
    
    List<UserDTO> searchUser(String search_key);
}

