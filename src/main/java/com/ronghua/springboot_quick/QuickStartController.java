package com.ronghua.springboot_quick;

import com.ronghua.springboot_quick.entity.Product;
import com.ronghua.springboot_quick.entity.ProductAttribute;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

//@RestController
//@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class QuickStartController {
    private List<Product> productDB = new ArrayList<>();

    @PostConstruct
    public void initDB(){
        productDB.add(new Product("B0001", "Android Development (Java)", 380));
        productDB.add(new Product("B0002", "Android Development (Kotlin)", 420));
        productDB.add(new Product("B0003", "Data Structure (Java)", 250));
        productDB.add(new Product("B0004", "Finance Management", 450));
        productDB.add(new Product("B0005", "Human Resource Management", 330));
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") String id){
//        Product product = new Product();
//        product.setId(id);
//        product.setName("Ronghua");
//        product.setPrice(23);
        Optional<Product> productOption = productDB.stream().filter(p->p.getId().equals(id)).findFirst();
        return productOption.map(product -> ResponseEntity.ok().body(product)).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product request) {
        boolean isIdDuplicated = productDB.stream()
                .anyMatch(p -> p.getId().equals(request.getId()));
        if (isIdDuplicated) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Product product = new Product();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        productDB.add(product);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(location).body(product);
    }

    //参数较少的时候可以使用的方法
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProductss(@RequestParam(value = "name", defaultValue = "", required = false) String name, @RequestParam(name = "price",required = false) int price){
        List<Product> productList = new ArrayList<>();
        for (Product product : productDB) {
            if (product.getName().toUpperCase().contains(name.toUpperCase()) || product.getPrice() == price) {
                productList.add(product);
            }
        }

        return ResponseEntity.ok().body(productList);
    }

    //参数比较多的时候可以用setter getter的方式
    @GetMapping("/productss")
    public ResponseEntity<List<Product>> getProducts(@ModelAttribute ProductAttribute productAttribute){
        String name = productAttribute.getName();
        String orderBy = productAttribute.getOrderBy();
        String rule = productAttribute.getSortRule();

        Comparator<Product> comparator;
        comparator = Objects.nonNull(orderBy) && Objects.nonNull(rule)? setComparable(orderBy, rule): (p1, p2) ->0;

        List<Product> productList = productDB.stream().filter(product -> product.getName().toLowerCase()
                .contains(name.toLowerCase()))
                .sorted(comparator)
                .collect(Collectors.toList());
        if(productList.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(productList);
    }

    private Comparator<Product> setComparable(String orderBy, String rule){
        Comparator<Product> comparator = null;
        if(orderBy.toLowerCase().equals("id"))
            comparator = Comparator.comparing(product -> product.getPrice());
        else if(orderBy.toLowerCase().equals("price"))
            comparator = Comparator.comparing(Product::getPrice);

        if(rule.toLowerCase().equals("desc"))
            comparator = comparator.reversed();
        return comparator;
    }

}
