package com.ronghua.springboot_quick.service;

import com.ronghua.springboot_quick.entity.auth.UserIdentity;
import com.ronghua.springboot_quick.entity.product.Product;
import com.ronghua.springboot_quick.entity.product.ProductAttribute;
import com.ronghua.springboot_quick.entity.product.ProductRequest;
import com.ronghua.springboot_quick.entity.product.ProductResponse;
import com.ronghua.springboot_quick.dao.ProductDao;
import com.ronghua.springboot_quick.exceptions.NotFoundException;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//@Service
public class ProductService {


//    private MockProductDao productDAO;
//    @Autowired
    private ProductDao productDao;
    private MailService mailService;
    //userIdentity can be accessed from Security Context
    private UserIdentity userIdentity;

    public ProductService(ProductDao productDao){
        this.productDao = productDao;
    }

    public ProductService(ProductDao productDao, MailService mailService) {
        this.productDao = productDao;
        this.mailService = mailService;
    }

    public ProductService(ProductDao productDao, MailService mailService, UserIdentity userIdentity) {
        this.productDao = productDao;
        this.mailService = mailService;
        this.userIdentity = userIdentity;
    }

    public ProductResponse getProduct(String id) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find product."));
        return ProductResponse.toProductResponse(product);
    }

    public List<ProductResponse> getProductsLikeName(String name){
        System.out.println("service start");
        List<Product> products = productDao.findByNameLikeIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException("There is no name like " + name));
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product product: products){
            productResponses.add(ProductResponse.toProductResponse(product));
        }
        System.out.println("service end");
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
                .orElseThrow(() -> new NotFoundException("No name like "+ nameKeyword + " was found"));
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
        product.setCreator(userIdentity.getEmail());
        product = productDao.insert(product);
        mailService.sendNewProductMail(product.getId());
        System.out.println("Creating product, MailService instance: "+ mailService.toString());
        return ProductResponse.toProductResponse(product);
    }

    public ProductResponse replaceProduct(String id, ProductRequest request) {
        ProductResponse oldProduct = getProduct(id);
        Product product = new Product();
        product.setId(oldProduct.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        mailService.sendReplaceProductMail(oldProduct.getId());
        System.out.println("Modifying product, MailService instance: "+ mailService.toString());
        productDao.save(product);
        return ProductResponse.toProductResponse(product);
    }

    public void deleteProduct(String id) {
        productDao.deleteById(id);
    }

}
