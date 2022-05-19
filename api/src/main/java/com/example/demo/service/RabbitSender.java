package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendReceiptData(Customer customer, Item item) throws JsonProcessingException {
        String receipt = customer.getUsername() + " " + item.getName();
        byte[] data = objectMapper.writeValueAsBytes(receipt);
        rabbitTemplate.convertAndSend("webShop", "api.#", data);
    }
}
