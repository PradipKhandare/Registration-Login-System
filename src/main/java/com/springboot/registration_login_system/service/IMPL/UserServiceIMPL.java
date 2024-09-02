package com.springboot.registration_login_system.service.IMPL;

import com.springboot.registration_login_system.dto.UserDTO;
import com.springboot.registration_login_system.entity.Role;
import com.springboot.registration_login_system.entity.User;
import com.springboot.registration_login_system.repository.RoleRepository;
import com.springboot.registration_login_system.repository.UserRepository;
import com.springboot.registration_login_system.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceIMPL implements UserService {
UserRepository userRepository;
RoleRepository roleRepository;

private PasswordEncoder passwordEncoder;

    public UserServiceIMPL(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserDTO user) {
        User newUser = new User();
        newUser.setName(user.getFirstName()+ " " + user.getLastName());
        newUser.setEmail(user.getEmail());
        //encrypt the password using spring security
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Role newRole = roleRepository.findByName("ROLE_ADMIN");
        if(newRole == null){
            newRole = checkRoleExistence();
        }
        newUser.setRoles(Arrays.asList(newRole));
        userRepository.save(newUser);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> mapToDTO(user)).collect(Collectors.toList());
    }

    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        String[] str = user.getName().split(" ");
        userDTO.setFirstName(str[0]);
        userDTO.setLastName(str[1]);
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    private Role checkRoleExistence() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        roleRepository.save(role);
        return role;
    }
}
