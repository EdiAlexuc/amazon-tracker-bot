package com.example.amazontracker;

import com.example.amazontracker.restservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoRepositories
@EnableScheduling
public class AmazonTrackerApplication {

    @Autowired
    ProductRepository productRepository;
    public static void main(String[] args) {
        SpringApplication.run(AmazonTrackerApplication.class, args);
    }

}
