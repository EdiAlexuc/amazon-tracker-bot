package com.example.amazontracker.restservice.repository;

import com.example.amazontracker.restservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{url: '?0'}")
    Product findItemByUrl(String url);
}
