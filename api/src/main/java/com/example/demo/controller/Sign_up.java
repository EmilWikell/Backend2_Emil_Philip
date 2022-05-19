package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController @RequiredArgsConstructor
public class Sign_up {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    CustomerRepo customerRepo;

    @PostMapping("/sign_up")
    public ResponseEntity<Void> signUp(@RequestBody Customer customer, UriComponentsBuilder uriComponentsBuilder){
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepo.save(customer);
        return ResponseEntity.created(uriComponentsBuilder.path("").build("")).build();
    }

    public void signUpDefaultUser(@RequestBody Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepo.save(customer);
    }
}
