package com.example.contactmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;// infinite loop se bachane ke liye
import jakarta.persistence.*;
import lombok.Setter;

import lombok.Data;

@Data
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}