package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemRepo itemRepo;

    @RequestMapping("/{id}")
    public Item getItemById(@PathVariable Long id){
        return itemRepo.findById(id).get();
    }

    @RequestMapping("")
    public Iterable<Item> getAllItems(){
        return itemRepo.findAll();
    }

    @PostMapping("")
    public String addItem(@RequestBody Item item){
        itemRepo.save(item);
        return "Item added";
    }
}
