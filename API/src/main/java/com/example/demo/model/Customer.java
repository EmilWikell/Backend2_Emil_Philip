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
    private String address;

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Customer() {

    }

    public Customer(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
