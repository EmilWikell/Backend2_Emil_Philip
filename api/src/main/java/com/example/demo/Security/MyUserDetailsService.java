package com.example.demo.Security;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepo.findCustomerByUsername(username);
        if (customer == null){
            throw new UsernameNotFoundException("Username not found");
        }else {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
            return new org.springframework.security.core.userdetails
                    .User(customer.getUsername(),customer.getPassword(), Arrays.asList(grantedAuthority));
        }
    }
}
