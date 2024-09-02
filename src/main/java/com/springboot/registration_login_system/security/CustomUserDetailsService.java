package com.springboot.registration_login_system.security;

import com.springboot.registration_login_system.entity.User;
import com.springboot.registration_login_system.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(emailOrUsername);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    user.getRoles()
                            .stream()
                            .map((role)->new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList()));
        }else{
            throw new UsernameNotFoundException("User not found");
        }
    }

}
