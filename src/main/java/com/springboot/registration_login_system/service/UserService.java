package com.springboot.registration_login_system.service;


import com.springboot.registration_login_system.dto.UserDTO;
import com.springboot.registration_login_system.entity.User;

import java.util.List;

public interface UserService {

    void registerUser(UserDTO user);

    User findUserByEmail(String email);

    List<UserDTO> findAllUsers();
}
