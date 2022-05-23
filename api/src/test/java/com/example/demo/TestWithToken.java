package com.example.demo;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest @AutoConfigureMockMvc
public class TestWithToken {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerRepo customerRepo;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @BeforeEach
    public void init(){
        String password = BCrypt.hashpw("pass",BCrypt.gensalt());
        Customer p1 = new Customer(1L, "user" ,password);
        when(customerRepo.findCustomerByUsername("user")).thenReturn(p1);

    }


    private ResultActions performWithLogin(MockHttpServletRequestBuilder builder) throws Exception {
        String token = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"user\", \"password\": \"pass\"}"))
                .andReturn().getResponse().getContentAsString();
        return mockMvc.perform(builder.header("Authorization", "Bearer " + token.substring(1, token.length()-1)));
    }

    @Test
    public void shouldUseToken() throws Exception {
        performWithLogin(get("/customers")).andExpect(status().isOk());
    }

}
