package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor
public class CustomerOrder {

    @GeneratedValue @Id
    private long Id;


    @ManyToOne @JoinColumn
    private Customer customer;

    @ManyToOne @JoinColumn
    private Item item;

    public CustomerOrder(long id , Customer customer, Item item) {
        this.Id = id;
        this.customer = customer;
        this.item = item;
    }

    public CustomerOrder(Customer customer, Item item) {
        this.customer = customer;
        this.item = item;
    }
}
