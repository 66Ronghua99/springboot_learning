package com.ronghua.springboot_quick.dao;

import com.ronghua.springboot_quick.entity.product.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDao extends MongoRepository<Product, String> {
    @Query("{'name': {'$regex': ?0, '$options': 'i'}}")
    Optional<List<Product>> findByNameLikeIgnoreCase(String productName);
    @Query(value = "{'name': {'$regex': '?0', '$options': 'i'}}")
    Optional<List<Product>> whatTheFuck(Sort sort, String name);
}
