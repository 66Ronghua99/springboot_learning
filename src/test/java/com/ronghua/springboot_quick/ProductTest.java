package com.ronghua.springboot_quick;

import com.ronghua.springboot_quick.entity.Product;
import com.ronghua.springboot_quick.dao.ProductDao;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductTest {

    private HttpHeaders httpHeaders;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        productDao.deleteAll();
    }

    @After
    public void clearAll(){
        productDao.deleteAll();
    }

    @Test
    public void testCreateProduct() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "What the Fuck I'd like to put");
        jsonObject.put("price", 520);

        RequestBuilder requestBuilder = post("/products")
                .headers(httpHeaders)
                .content(jsonObject.toString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.name").value(jsonObject.getString("name")))
                .andExpect(jsonPath("$.price").value(jsonObject.getInt("price")))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Content-Type", "application/json"));
    }

    @Test
    public void testGetProduct() throws Exception {
        Product product = createProduct("Economics", 550);
        productDao.insert(product);
        mockMvc.perform(get("/products/"+product.getId()))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Economics"))
                .andExpect(jsonPath("$.price").value(550))
                .andExpect(header().string("Content-Type", "application/json"));
    }

    @Test
    public void testDeleteProduct(){

    }

    @Test
    public void testReplaceProduct() throws Exception {
        Product product = createProduct("Economics", 450);
        productDao.insert(product);

        JSONObject request = new JSONObject();
        request.put("name", "Macroeconomics");
        request.put("price", 550);

        mockMvc.perform(put("/products/" + product.getId())
                .headers(httpHeaders)
                .content(request.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(request.getString("name")))
                .andExpect(jsonPath("$.price").value(request.getInt("price")));
    }

    @Test
    public void testSearchProductsSortByPriceAsc() throws Exception {
        Product p1 = createProduct("Operation Management", 350);
        Product p2 = createProduct("Marketing Management", 200);
        Product p3 = createProduct("Human Resource Management", 420);
        Product p4 = createProduct("Finance Management", 400);
        Product p5 = createProduct("Enterprise Resource Planning", 440);
        System.out.println(p1.getId());
        productDao.insert(Arrays.asList(p1, p2, p3, p4, p5));

        mockMvc.perform(get("/products/sort")
                .headers(httpHeaders)
                .param("name", "Manage")
                .param("orderBy", "price")
                .param("sortRule", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id").value(p2.getId()))
                .andExpect(jsonPath("$[1].id").value(p1.getId()))
                .andExpect(jsonPath("$[2].id").value(p4.getId()))
                .andExpect(jsonPath("$[3].id").value(p3.getId()));
    }

    @Test
    public void get400WhenCreateProductWithEmptyName() throws Exception {
        JSONObject request = new JSONObject();
        request.put("name", "");
        request.put("price", 350);

        mockMvc.perform(post("/products")
                .headers(httpHeaders)
                .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void get400WhenReplaceProductWithNegativePrice() throws Exception {
        Product product = createProduct("Computer Science", 350);
        productDao.insert(product);

        JSONObject request = new JSONObject();
        request.put("name", "Computer Science");
        request.put("price", -100);

        mockMvc.perform(put("/products/" + product.getId())
                .headers(httpHeaders)
                .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    private Product createProduct(String name, int price) {
        Product product = new Product();
//        product.setId(String.valueOf(new Random().nextInt()));
        product.setName(name);
        product.setPrice(price);

        return product;
    }


}
