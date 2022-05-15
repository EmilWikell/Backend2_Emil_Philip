package com.example.demo;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerOrderRepo;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.ItemRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demo(CustomerOrderRepo customerOrderRepo, CustomerRepo customerRepo, ItemRepo itemRepo){
        return (args) ->{
            Customer customer0 = new Customer("Goran", "123");
            Customer customer1 = new Customer("Nils", "123");
            Customer customer2 = new Customer("Lena" , "123");
//
    //        Item item0 = new Item("Hammare","2020");
    //        Item item1 = new Item("Spik","2021");
    //        Item item2 = new Item("Slägga","2022");
    //        Item item3 = new Item("Yxa","2023");
    //        Item item4 = new Item("Såg","2024");
//
    //        CustomerOrder customerOrder0 = new CustomerOrder("1010", customer0,item0);
    //        CustomerOrder customerOrder1 = new CustomerOrder("1011", customer0,item1);
    //        CustomerOrder customerOrder2 = new CustomerOrder("1012", customer0,item4);
    //        CustomerOrder customerOrder3 = new CustomerOrder("1013", customer2,item4);
    //        CustomerOrder customerOrder4 = new CustomerOrder("1014", customer2,item3);
//
            customerRepo.save(customer0);
           customerRepo.save(customer1);
            customerRepo.save(customer2);
//
    //        itemRepo.save(item0);
    //        itemRepo.save(item1);
    //        itemRepo.save(item2);
    //        itemRepo.save(item3);
    //        itemRepo.save(item4);
//
    //        customerOrderRepo.save(customerOrder0);
    //        customerOrderRepo.save(customerOrder1);
    //        customerOrderRepo.save(customerOrder2);
    //        customerOrderRepo.save(customerOrder3);
    //        customerOrderRepo.save(customerOrder4);
       };
    }
}
