package com.example.demo.controller;

import com.example.demo.repository.CustomerRepo;
import com.example.demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class sign_up {

    @Autowired
    CustomerRepo customerRepo;

    @PostMapping("/sign_up")
    public ResponseEntity<Void> signUp(@RequestBody Customer customer, UriComponentsBuilder uriComponentsBuilder){
        customerRepo.save(customer);
        return ResponseEntity.created(uriComponentsBuilder.path("").build("")).build();
    }
}
