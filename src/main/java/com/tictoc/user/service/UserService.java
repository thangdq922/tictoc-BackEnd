package com.tictoc.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.tictoc.user.UserDTO;

public interface UserService {
    UserDTO saveUser(UserDTO userDto, MultipartFile upAvatar);
    
    UserDTO saveFieldUser(Map<String, Object> fields, MultipartFile upAvatar);
    
    UserDTO findById(Long id);

    UserDTO findByUserName(String username);

    List<UserDTO> findAllUsers(Pageable pageable);
    
    void deleteUsers(long[] ids);
    
    List<UserDTO> searchUser(String search_key, Pageable pageable);
    
    UserDTO follow(Long id);
    
    UserDTO unfollow(Long id);
}

