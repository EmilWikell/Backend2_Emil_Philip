package com.example.demo.Controller.NewTest;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class TestGetWithoutToken {


    @Autowired
    private MockMvc mockMvc;



    private ResultActions performWithInvalidToken(MockHttpServletRequestBuilder builder) throws Exception {
        String token = "eyJ0eXAiOoJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNYWxpbiIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvbG9naW4iLCJleHAiOjE2NTI4OTg1ODl9.f2SNChC-yB1_44VToSOOlFWT8e-pYMyTOQ7zPbSp7d0";
        return mockMvc.perform(builder.header("Authorization", "Bearer " + token));
    }



      @Test
      void failGetAllCustomersTest() throws Exception {
        performWithInvalidToken(get("/customers")).andExpect(status().isForbidden());

      }




    @MockBean
    private CustomerRepo customerRepo;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void init(){
        Customer p1 = new Customer(1L, "user" , bCryptPasswordEncoder.encode("pass"));
        when(customerRepo.findCustomerByUsername("user")).thenReturn(p1);

    }

    private ResultActions performWithLogin(MockHttpServletRequestBuilder builder) throws Exception {
        String token = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"user\", \"password\": \"pass\"}"))
                .andReturn().getResponse().getContentAsString();
        return mockMvc.perform(builder.header("Authorization", "Bearer " + token));
    }

    @Test
    public void shouldUseToken() throws Exception {
        performWithLogin(get("/customers"))
                .andExpect(status().isOk());
    }

}
