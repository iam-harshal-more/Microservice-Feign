package org.example.userservice.service;

import org.example.userservice.model.UserDetails;
import org.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDetails> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserDetails> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserDetails createUser(UserDetails user) {
        return userRepository.save(user);
    }
public UserDetails updateUser(Long id, UserDetails userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userDetails.getName());
                    user.setEmail(userDetails.getEmail());
                    user.setPhone(userDetails.getPhone());
                    user.setAddress(userDetails.getAddress());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    userDetails.setId(id);
                    return userRepository.save(userDetails);
                });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}