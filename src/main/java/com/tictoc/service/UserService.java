package com.tictoc.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.tictoc.dto.UserDTO;

public interface UserService {
    UserDTO saveUser(UserDTO userDto);
    
    UserDTO saveFieldUser(Map<String, Object> fields);
    
    UserDTO findById(Long id);

    UserDTO findByUserName(String username);
    
    UserDTO findByUserNameOrEmail(String username, String email);
        
    List<UserDTO> findUserFollowing(Pageable pageable);

    List<UserDTO> findAllUsers(Pageable pageable);
    
    List<UserDTO> findSuggestedUsers(Pageable pageable);
    
    void deleteUsers(Long id);
    
    List<UserDTO> searchUser(String search_key, Pageable pageable);
    
    UserDTO follow(Long id);
    
    UserDTO unfollow(Long id);
}

