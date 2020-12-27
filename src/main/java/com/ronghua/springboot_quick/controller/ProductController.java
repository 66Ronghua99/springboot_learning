package com.ronghua.springboot_quick.controller;

import com.ronghua.springboot_quick.entity.product.ProductAttribute;
import com.ronghua.springboot_quick.entity.product.ProductRequest;
import com.ronghua.springboot_quick.entity.product.ProductResponse;
import com.ronghua.springboot_quick.service.MailService;
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

    @Autowired
    private MailService mailService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") String id) {
        System.out.println("Getting product, Service instance: "+ productService.toString());
        ProductResponse productResponse = productService.getProduct(id);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@RequestParam(name="name", required = false, defaultValue = "") String nameLike){
        System.out.println("Getting products with name like "+ nameLike);
        List<ProductResponse> productResponses = productService.getProductsLikeName(nameLike);
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<ProductResponse>> getProductsAndSort(@ModelAttribute ProductAttribute param){
        List<ProductResponse> productResponses = productService.getProductsAndSort(param);

        return ResponseEntity.ok(productResponses);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        System.out.println("Creating product, Service instance: "+ productService.toString());
        ProductResponse productResponse = productService.createProduct(request);
        mailService.sendNewProductMail(productResponse.getId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productResponse.getId())
                .toUri();

        return ResponseEntity.created(location).body(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> replaceProduct(
            @PathVariable("id") String id, @Valid @RequestBody ProductRequest request) {
        System.out.println("Replacing product");
        ProductResponse productResponse = productService.replaceProduct(id,request);
        mailService.sendReplaceProductMail(id);
        return ResponseEntity.ok(productResponse);
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
