package org.example.jwtauth.service;

import lombok.RequiredArgsConstructor;
import org.example.jwtauth.model.User;
import org.example.jwtauth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
