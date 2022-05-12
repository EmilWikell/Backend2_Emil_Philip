package com.example.demo.repository;

import com.example.demo.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepo extends CrudRepository<Item, Long> {

    Item findItemByName(String name);
}
