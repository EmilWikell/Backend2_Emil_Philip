package com.example.demo.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity @Data @NoArgsConstructor
public class Customer {

    @GeneratedValue @Id
    private long id;
    @Column(unique = true)
    private String username;
    private String password;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public Customer(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
