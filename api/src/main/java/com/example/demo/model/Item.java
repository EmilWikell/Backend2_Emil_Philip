package com.example.demo.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity @Data @NoArgsConstructor
public class Item {

    @GeneratedValue @Id
    private long id;
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public Item(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
