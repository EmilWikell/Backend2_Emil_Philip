package com.example.demo.controller;
//Hello
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

//Controller
@RestController
@RequestMapping("/customers")
public class CustomerController implements UserDetailsService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepo.findCustomersByUsername(username);
        if (customer == null){
            throw new UsernameNotFoundException("User not found");
        }else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(), authorities);
        }
    }

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

    @PostMapping("")
    public String addCustomer(@RequestBody Customer customer){
        //customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customerRepo.save(customer);
        return "Customer added";

    }
}
