package com.example.demo.controller;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepo customerRepo;

    @RequestMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        return customerRepo.findById(id).get();

    }

    @RequestMapping("")
    public Iterable<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }


}
