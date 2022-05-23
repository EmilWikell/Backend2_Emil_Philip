package com.example.demo.controller;

import com.example.demo.model.BuyObject;
import com.example.demo.model.Customer;
import com.example.demo.model.CustomerOrder;
import com.example.demo.model.Item;
import com.example.demo.repository.CustomerOrderRepo;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.ItemRepo;
import com.example.demo.service.RabbitSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    CustomerOrderRepo customerOrderRepo;

    @Autowired
    CustomerRepo customerRepo;

    @RequestMapping("/{id}")
    public Item getItemById(@PathVariable Long id){
        return itemRepo.findById(id).get();
    }

    @PostMapping("/buy")
    public ResponseEntity<Void> buyItem(@RequestBody BuyObject buyObject, UriComponentsBuilder uriComponentsBuilder){
        if(customerRepo.findById(buyObject.getCustomer()).isPresent()
                && itemRepo.findById(buyObject.getItem()).isPresent()) {
            Customer customer = customerRepo.findById(buyObject.getCustomer()).get();
            Item item = itemRepo.findById(buyObject.getItem()).get();
            CustomerOrder order = new CustomerOrder(customer, item);
            customerOrderRepo.save(order);

            try {
                System.out.println("inside");
                rabbitSender.sendReceiptData(customer, item);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return ResponseEntity
                .created(uriComponentsBuilder.path("orders/{id}").build(order.getId()))
                .build();
        }
        return ResponseEntity
                .created(uriComponentsBuilder.path("orders/failed").build("failed")).build();
    }

    @RequestMapping("")
    public Iterable<Item> getAllItems(){
        return itemRepo.findAll();
    }

    @PostMapping("")
    public String addItem(@RequestBody Item item){
        itemRepo.save(item);
        return "Item added";
    }
}
