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
    private String name;
    private String password;

    public Customer(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Customer() {

    }

    public Customer(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
