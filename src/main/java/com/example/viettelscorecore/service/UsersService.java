package com.example.viettelscorecore.service;

import com.example.viettelscorecore.model.Users;
import com.example.viettelscorecore.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users createUser(Users user) {
        return usersRepository.save(user);
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Users getUserById(Long id) {

        simulateSlowQuery();
        return usersRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));
    }

    public Users updateUser(Long id, Users request) {
        Users user = getUserById(id);

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());

        return usersRepository.save(user);
    }

    public void deleteUser(Long id) {
        Users user = getUserById(id);
        usersRepository.delete(user);
    }

    private void simulateSlowQuery() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}