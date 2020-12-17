package com.ronghua.springboot_quick.controller;

import com.ronghua.springboot_quick.Utils.Product;
import com.ronghua.springboot_quick.Utils.ProductAttribute;
import com.ronghua.springboot_quick.Utils.ProductRequest;
import com.ronghua.springboot_quick.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
        System.out.println("Getting product");
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(name="name", required = false) String nameLike){
        System.out.println("Getting products with name like "+ nameLike);
        List<Product> products = productService.getProductsLikeName(nameLike);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Product>> getProductsAndSort(@ModelAttribute ProductAttribute param){
        List<Product> productList = productService.getProductsAndSort(param);
        return ResponseEntity.ok(productList);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request) {
        System.out.println("Creating product");
        Product product = productService.createProduct(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(location).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(
            @PathVariable("id") String id, @Valid @RequestBody Product request) {
        System.out.println("Replacing product");
        Product product = productService.replaceProduct(id, request);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") String id) {
        System.out.println("Deleting product");
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping
//    public ResponseEntity<List<Product>> getProducts(@ModelAttribute ProductAttribute param) {
//        List<Product> products = productService.getProducts(param);
//        return ResponseEntity.ok(products);
//    }

}
