package com.example.contactmanager.controller;

import com.example.contactmanager.model.Contact;
import com.example.contactmanager.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping("/{userId}")
    public Contact create(@PathVariable Long userId, @RequestBody Contact contact) {
        return service.create(userId, contact);
    }

    @GetMapping("/{userId}")
    public List<Contact> getAll(@PathVariable Long userId) {
        return service.getAll(userId);
    }

    @GetMapping("/search")
    public List<Contact> search(
            @RequestParam Long userId,
            @RequestParam String keyword) {

        return service.search(userId, keyword);
    }

    @PutMapping("/{id}")
    public Contact update(@PathVariable Long id, @RequestBody Contact contact) {
        return service.update(id, contact);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}