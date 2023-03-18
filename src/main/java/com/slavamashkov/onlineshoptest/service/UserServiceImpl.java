package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Role;
import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.repository.RoleRepository;
import com.slavamashkov.onlineshoptest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User findByUsername(String username) {
        User user = userRepository.getUserByUsername(username);

        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Could not find user");
        }
    }

    @Override
    public void save(User userDto) {
        User user = userRepository.getUserByEmail(userDto.getEmail());

        if (user != null) {
            userRepository.save(user);
        } else {
            Role role = roleRepository.findByName("ROLE_USER");

            user = User.builder()
                    .username(userDto.getUsername())
                    .email(userDto.getEmail())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .balance(0.0)
                    .enabled(true)
                    .roles(Set.of(role))
                    .build();

            userRepository.save(user);
        }
    }
}
