package com.example.contactmanager.controller;

import com.example.contactmanager.model.User;
import com.example.contactmanager.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return repo.save(user);
    }

    @GetMapping
    public List<User> getAll() {
        return repo.findAll();
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User updatedUser) {

        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(updatedUser.getUsername());

        return repo.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}