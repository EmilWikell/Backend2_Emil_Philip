package com.example.demo.Controller.OldTest;

import com.example.demo.model.Customer;
import com.example.demo.model.CustomerOrder;
import com.example.demo.model.Item;
import com.example.demo.repository.CustomerOrderRepo;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.ItemRepo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemRepo itemRepo;
    @MockBean
    private CustomerRepo customerRepo;
    @MockBean
    private CustomerOrderRepo customerOrderRepo;


    @BeforeEach
    public void init(){
        Item i0 = new Item(1L, "Hammare");
        Item i1 = new Item(1L, "Gräsklippare");
        Customer c1 = new Customer(1L, "Magnus" , "Gatan 3");
        Item i2 = new Item(2L, "Spik");
        Item i3 = new Item(3L, "Yxa");
        when(itemRepo.findItemByName("Gräsklippare")).thenReturn(i1);
        when(itemRepo.findById(1L)).thenReturn(Optional.of(i1));
        when(customerRepo.findById(1L)).thenReturn(Optional.of(c1));
        when(itemRepo.findAll()).thenReturn(List.of(i0, i2, i3));

    }

    @Test
    void getItemById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/items/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Gräsklippare\"}"));
    }


    @Test
    void buyItemTestSuccessEndpoint() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/items/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customer\":1,\"item\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("0")));
    }
    @Test
    void buyItemTestFailedEndpoint() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/items/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customer\":2,\"item\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("failed")));
    }

    @Test
    void getAllItems() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/items").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[" +
                        "{\"id\":1,\"name\":\"Hammare\"}," +
                        "{\"id\":2,\"name\":\"Spik\"}," +
                        "{\"id\":3,\"name\":\"Yxa\"}" +
                        "]")));

    }

    @Test
    void addItemTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                .post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Hammare\",\"articleNr\": \"-2020}\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(equalTo("Item added")));
    }
}