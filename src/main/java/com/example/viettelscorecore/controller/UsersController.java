package com.example.viettelscorecore.controller;

import com.example.viettelscorecore.model.Users;
import com.example.viettelscorecore.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping
    public Users create(@RequestBody Users user) {
        return usersService.createUser(user);
    }

    @GetMapping
    public List<Users> getAll() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "products", key = "#id")
    public Users getById(@PathVariable Long id) {
        return usersService.getUserById(id);
    }

    @PutMapping("/{id}")
    public Users update(
            @PathVariable Long id,
            @RequestBody Users user) {

        return usersService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "Deleted user with id = " + id;
    }
}