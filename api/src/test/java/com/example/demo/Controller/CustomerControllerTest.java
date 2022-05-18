package com.example.demo.Controller;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc
@SpringBootTest
class CustomerControllerTest {

    @MockBean
    private CustomerRepo customerRepo;


    @BeforeEach
    public void init(){
        Customer p1 = new Customer(1L, "Magnus" , "Gatan 3");
        Customer p2 = new Customer(2L, "Stina" , "Backen 32");
        Customer p3 = new Customer(3L, "Maja" , "Stigen 64");
       when(customerRepo.findAll()).thenReturn(Arrays.asList(p1,p2,p3));
        when(customerRepo.findById(1L)).thenReturn(Optional.of(p1));

    }

    @Autowired
   private MockMvc mvc;

    @Test
    void getCustomerById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/customers/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1,\"name\":\"Magnus\",\"password\":\"Gatan 3\"}")));
    }

    @Test
   void getAllCustomers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/customers").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                       "{\"id\":1,\"name\":\"Magnus\",\"password\":\"Gatan 3\"}," +
                        "{\"id\":2,\"name\":\"Stina\",\"password\":\"Backen 32\"}," +
                        "{\"id\":3,\"name\":\"Maja\",\"password\":\"Stigen 64\"}" +
                        "]"));
    }

    @Test
   void addCustomerTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                        .post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Osckar\",\"password\": \"Flyggatan 1}\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(equalTo("Customer added")));
    }
}