package com.example.demo.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Item {

    @GeneratedValue
    @Id
    private long id;
    private String name;
    private String articleNr;

    public Item(String name, String articleNr) {
        this.name = name;
        this.articleNr = articleNr;
    }

    public Item() {

    }

    public Item(long id, String name, String articleNr) {
        this.id = id;
        this.name = name;
        this.articleNr = articleNr;
    }
}
