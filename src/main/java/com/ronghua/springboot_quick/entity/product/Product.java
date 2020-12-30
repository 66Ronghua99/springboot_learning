package com.ronghua.springboot_quick.entity.product;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {
    private String id;
    private int price;
    private String name;
    private String creator;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Product(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public Product(){

    }

    public Product(String id, String name, int price) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
