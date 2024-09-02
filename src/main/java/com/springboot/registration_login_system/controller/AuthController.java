package com.springboot.registration_login_system.controller;


import com.springboot.registration_login_system.dto.UserDTO;
import com.springboot.registration_login_system.entity.User;
import com.springboot.registration_login_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    //To handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    //To handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return "register";
    }

    //To handle user registration form submission request

    @PostMapping("/register/save")
    public String registerUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model){

        User existing = userService.findUserByEmail(userDTO.getEmail());
        if(existing != null){
            bindingResult.rejectValue("email", null, "This email address is already in use");
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("userDTO", userDTO);
            return "/register";
        }

        userService.registerUser(userDTO);
        return "redirect:/register?success";
    }


    //To handle list of users
    @GetMapping("/users")
    public String users(Model model){
        List<UserDTO> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    //To handle the login request
    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
