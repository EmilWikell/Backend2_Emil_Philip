package com.example.demo.repository;

import com.example.demo.model.CustomerOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerOrderRepo extends CrudRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByCustomerId(Long id);




}
