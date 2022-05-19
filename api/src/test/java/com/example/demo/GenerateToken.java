package com.example.demo;

import com.example.demo.Security.CustomAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenerateToken {

    @Test
    void testGenerateToken(){
        User user = new User("Jan","123", Collections.emptyList());
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        String token = customAuthenticationFilter.generateToken(user);
        assertEquals(3, token.split("\\.").length);
    }
}
