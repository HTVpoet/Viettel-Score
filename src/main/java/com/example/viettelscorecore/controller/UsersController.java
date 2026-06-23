package com.example.viettelscorecore.controller;

import com.example.viettelscorecore.model.Users;
import com.example.viettelscorecore.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersRepository userRepository;

    // CREATE
    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return userRepository.save(user);
    }

    // READ ALL
    @GetMapping
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Optional<Users> getUserById(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Users updateUser(@PathVariable Long id, @RequestBody Users newUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setPhone(newUser.getPhone());
                    user.setPassword(newUser.getPassword());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "Deleted user id: " + id;
    }
}