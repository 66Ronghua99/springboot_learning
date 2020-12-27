package com.ronghua.springboot_quick.dao;

import com.ronghua.springboot_quick.entity.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepoImpl {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Product> findByNameLikeIgnoreCase(String name, Sort sort){
        Criteria find = Criteria.where("name").regex(name, "i");
        Query query = new Query().addCriteria(find).with(sort);
        return mongoTemplate.find(query, Product.class);
    }


}
