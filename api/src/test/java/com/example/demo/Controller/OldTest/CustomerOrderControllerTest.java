package com.example.demo.Controller.OldTest;

import com.example.demo.model.Customer;
import com.example.demo.model.CustomerOrder;
import com.example.demo.model.Item;
import com.example.demo.repository.CustomerOrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CustomerOrderControllerTest {

    @MockBean
    private CustomerOrderRepo customerOrderRepo;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    private void init(){
        Customer customer = new Customer(1L, "Magnus" , "Gatan 3");
        Customer customer2 = new Customer(2L, "Stina" , "Backen 32");

        Item item1 = new Item(1L, "Hammare");
        Item item2 = new Item(2L, "Spik");

        CustomerOrder customerOrder1 = new CustomerOrder(1L, customer,item1);
        CustomerOrder customerOrder2 = new CustomerOrder(2L, customer,item2);
        CustomerOrder customerOrder3 = new CustomerOrder(3L, customer2,item2);

        when(customerOrderRepo.findAll()).thenReturn(List.of(customerOrder1, customerOrder2, customerOrder3));
        when(customerOrderRepo.findByCustomerId(2L)).thenReturn(List.of(customerOrder3));
    }

    @Test
    void getAllCustomerOrders() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/orders").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{\"customer\":{\"id\":1,\"name\":\"Magnus\",\"password\":\"Gatan 3\"}," +
                        "\"item\":{\"id\":1,\"name\":\"Hammare\"},\"id\":1},{" +
                        "\"customer\":{\"id\":1,\"name\":\"Magnus\",\"password\":\"Gatan 3\"}," +
                        "\"item\":{\"id\":2,\"name\":\"Spik\"},\"id\":2},{" +
                        "\"customer\":{\"id\":2,\"name\":\"Stina\",\"password\":\"Backen 32\"},\"item\":{\"id\":2," +
                        "\"name\":\"Spik\"},\"id\":3}]"));
    }

    @Test
    void getAllCustomerOrdersByCustomerId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/orders/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{\"customer\":{\"id\":2,\"name\":\"Stina\",\"password\":\"Backen 32\"}," +
                        "\"item\":{\"id\":2,\"name\":\"Spik\"},\"id\":3}]"));
    }
}