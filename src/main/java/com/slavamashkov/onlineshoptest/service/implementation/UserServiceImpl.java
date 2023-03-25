package com.slavamashkov.onlineshoptest.service.implementation;

import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.repository.UserRepository;
import com.slavamashkov.onlineshoptest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);

        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Could not find user");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such user with id: " + id));
    }
}
