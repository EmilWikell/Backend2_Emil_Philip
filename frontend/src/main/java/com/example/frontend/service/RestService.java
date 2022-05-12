package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Controller
@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Value("${api_base_url:http://localhost:8080/}")
    private String apiBaseUrl;
    public Product[] getPostsPlainJSON() {
        return this.restTemplate.getForObject(apiBaseUrl + "items", Product[].class);
    }

    @RequestMapping("/products")
    public String demo(Model model){
        Product[] p = getPostsPlainJSON();
        model.addAttribute("Products", p);
        model.addAttribute("Productlist", "all products");
        return "product";
    }
}