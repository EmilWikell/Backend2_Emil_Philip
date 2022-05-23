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

import java.util.List;

@RestController
@RequestMapping("/orders")
public class CustomerOrderController {

    @Autowired
    private RabbitSender rabbitSender;
    @Autowired
    ItemRepo itemRepo;
    @Autowired
    CustomerOrderRepo customerOrderRepo;
    @Autowired
    CustomerRepo customerRepo;

    @RequestMapping("")
    public Iterable<CustomerOrder> getAllCustomerOrders() {
        return customerOrderRepo.findAll();
    }

    @RequestMapping("/{customerId}")
    public List<CustomerOrder> getAllCustomerOrdersByCustomerId(@PathVariable Long customerId){
        return customerOrderRepo.findByCustomerId(customerId);
    }

    @PostMapping("")
    public ResponseEntity<Void> buyItem(@RequestBody BuyObject buyObject, UriComponentsBuilder uriComponentsBuilder){
        if(customerRepo.findById(buyObject.getCustomer()).isPresent()
                && itemRepo.findById(buyObject.getItem()).isPresent()) {
            Customer customer = customerRepo.findById(buyObject.getCustomer()).get();
            Item item = itemRepo.findById(buyObject.getItem()).get();
            CustomerOrder order = new CustomerOrder(customer, item);
            customerOrderRepo.save(order);

            try {
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

}
