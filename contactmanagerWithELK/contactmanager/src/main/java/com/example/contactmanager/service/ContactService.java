package com.example.contactmanager.service;

import com.example.contactmanager.model.Contact;
import com.example.contactmanager.model.User;
import com.example.contactmanager.repository.ContactRepository;
import com.example.contactmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class ContactService {

    private final ContactRepository contactRepo;
    private final UserRepository userRepo;

    public ContactService(ContactRepository contactRepo, UserRepository userRepo) {
        this.contactRepo = contactRepo;
        this.userRepo = userRepo;
    }

    private static final Logger log =
            LoggerFactory.getLogger(ContactService.class);



    public Contact create(Long userId, Contact contact) {

        log.info("Creating contact {} for user {}",
                contact.getName(), userId);

        User user = userRepo.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id {}", userId);
                    return new RuntimeException("User not found");
                });

        if (!isValidPhone(contact.getPhone())) {

            log.error("Invalid phone number {}",
                    contact.getPhone());

            throw new RuntimeException("Invalid phone number");
        }

        if (contactRepo.existsByUserIdAndNameIgnoreCase(userId, contact.getName())) {

            log.warn("Duplicate contact name {} for user {}",
                    contact.getName(), userId);

            throw new RuntimeException("Duplicate name");
        }

        contact.setUser(user);

        log.info("Saving contact {}",
                contact.getName());

        return contactRepo.save(contact);
    }

    public List<Contact> getAll(Long userId) {
        return contactRepo.findByUserId(userId);
    }

//    public List<Contact> search(Long userId, String query) {
//
//        log.info("Searching contacts for user {} with keyword {}",
//                userId, query);
//
//        List<Contact> contacts = contactRepo.findByUserId(userId);
//
//        List<Contact> result = contacts.stream()
//                .filter(c ->
//                        c.getName() != null &&
//                                c.getName().toLowerCase()
//                                        .contains(query.toLowerCase())
//                )
//                .toList();
//
//        log.info("Found {} contacts for keyword {}",
//                result.size(), query);
//
//        return result;
//    }

    public List<Contact> search(Long userId, String query) {
        log.info("Searching contacts in DB for user {} with keyword {}", userId, query);

        // No more stream filtering! The DB does the work now.
        return contactRepo.findByUserIdAndNameContainingIgnoreCase(userId, query);
    }

    public Contact update(Long id, Contact updated) {

        Contact c = contactRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        if (!isValidPhone(updated.getPhone())) {
            throw new RuntimeException("Invalid phone");
        }

        c.setName(updated.getName());
        c.setEmail(updated.getEmail());
        c.setPhone(updated.getPhone());

        return contactRepo.save(c);
    }

    public void delete(Long id) {

        log.warn("Deleting contact with id {}", id);

        contactRepo.deleteById(id);

        log.info("Contact deleted successfully with id {}", id);
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^[0-9]{10}$");
    }


}