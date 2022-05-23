package com.example.demo.repository;

import com.example.demo.model.Item;
import org.springframework.data.repository.CrudRepository;


public interface ItemRepo extends CrudRepository<Item, Long> {

    Item findItemByName(String name);
}
