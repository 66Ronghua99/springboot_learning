package com.ronghua.springboot_quick.service;

import com.ronghua.springboot_quick.Utils.Product;
import com.ronghua.springboot_quick.Utils.ProductAttribute;
import com.ronghua.springboot_quick.Utils.ProductRequest;
import com.ronghua.springboot_quick.Utils.ProductResponse;
import com.ronghua.springboot_quick.dao.ProductDao;
import com.ronghua.springboot_quick.dao.ProductRepoImpl;
import com.ronghua.springboot_quick.exceptions.NotFountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {


//    private MockProductDao productDAO;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductRepoImpl productRepo;

//    public Product createProduct(Product request) {
//        boolean isIdDuplicated = productDAO.find(request.getId()).isPresent();
//        if (isIdDuplicated) {
//            throw new ConflictException("The id of the product is duplicated.");
//        }
//
//        Product product = new Product();
//        product.setId(request.getId());
//        product.setName(request.getName());
//        product.setPrice(request.getPrice());
//
//        return productDAO.insert(product);
//    }
//
//
//    public Product replaceProduct(String id, Product request) {
//        Product product = getProduct(id);
//        return productDAO.replace(product.getId(), request);
//    }
//
//    public void deleteProduct(String id) {
//        Product product = getProduct(id);
//        productDAO.delete(product.getId());
//    }
//
//    public List<Product> getProducts(ProductAttribute param) {
//        return productDao.find(param);
//    }

    public ProductResponse getProduct(String id) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new NotFountException("Can't find product."));
        return ProductResponse.toProductResponse(product);
    }

    public List<ProductResponse> getProductsLikeName(String name){
        List<Product> products = productDao.findByNameLikeIgnoreCase(name)
                .orElseThrow(() -> new NotFountException("There is no name like " + name));
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product product: products){
            productResponses.add(ProductResponse.toProductResponse(product));
        }
        return productResponses;
    }

    public List<ProductResponse> getProductsAndSort(ProductAttribute param) {
        String nameKeyword = Optional.ofNullable(param.getName()).orElse("");
        String orderBy = param.getOrderBy();
        String sortRule = param.getSortRule();
        Sort sort = Sort.unsorted();
        if (Objects.nonNull(orderBy) && Objects.nonNull(sortRule)) {
            Sort.Direction direction = Sort.Direction.fromString(sortRule);
            sort = Sort.by(direction, orderBy);
            System.out.println(sort.toString());
        }
        List<Product> products = productDao.whatTheFuck(sort, nameKeyword)
                .orElseThrow(() -> new NotFountException("No name like "+ nameKeyword + " was found"));
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product product: products){
            productResponses.add(ProductResponse.toProductResponse(product));
        }
        return productResponses;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product = productDao.insert(product);
        return ProductResponse.toProductResponse(product);
    }

    public ProductResponse replaceProduct(String id, Product request) {
        ProductResponse oldProduct = getProduct(id);
        Product product = new Product();
        product.setId(oldProduct.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        productDao.save(product);
        return ProductResponse.toProductResponse(product);
    }

    public void deleteProduct(String id) {
        productDao.deleteById(id);
    }

//    public class Sort{
//        private final List<SortPair> sortList;
//
//        public void sortRule(String... param){
//            if(param.length%2 != 0)
//                throw new RuntimeException("sortBy() parameters should be in pairs!");
//            for(int i=0; i<param.length; i=i+2){
//                sortList.add(new SortPair(param[i], param[i+1]));
//            }
//        }
//
//        public Sort(){
//            sortList = new ArrayList<>();
//        }
//
//    }
//    private class SortPair{
//        private String key;
//        private String order;
//
//        public SortPair(String key) {
//            this.key = key;
//            order = "asc";
//        }
//
//        public SortPair(String key, String order) {
//            this.key = key;
//            this.order = order;
//        }
//
//        public String getKey() {
//            return key;
//        }
//
//        public void setKey(String key) {
//            this.key = key;
//        }
//
//        public String getOrder() {
//            return order;
//        }
//
//        public void setOrder(String order) {
//            this.order = order;
//        }
//    }
}
