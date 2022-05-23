package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest @AutoConfigureMockMvc
class TestWithInvalidToken {
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
}
