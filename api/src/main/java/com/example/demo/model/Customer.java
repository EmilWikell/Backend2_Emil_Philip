package com.example.demo.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Customer {

    @GeneratedValue
    @Id
    private long id;
    private String username;
    private String address;
    private String password;

    public Customer(String userName, String password) {
        this.username = userName;
        this.password = password;
    }



    public Customer() {

    }

    public Customer(long id, String userName, String address) {
        this.id = id;
        this.username = userName;
        this.address = address;
    }
}
