package com.ronghua.springboot_quick;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class ObjectMapperTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerializeBookToJSON() throws Exception {
        Book book = new Book();
        Publisher publisher = new Publisher();
        publisher.setAddress("Hongkong");
        publisher.setTel("19283746587");
        publisher.setCompanyName("Cannot imaging a name");
        book.setId("B0001");
        book.setName("Computer Science");
        book.setPrice(350);
        book.setIsbn("978-986-123-456-7");
        book.setCreatedTime(new Date());
        book.setPublisher(publisher);

        String strBookJSON = mapper.writeValueAsString(book);
        JSONObject bookJSON = new JSONObject(strBookJSON);
        System.out.println(strBookJSON);
        Assert.assertEquals(book.getId(), bookJSON.getString("id"));
        Assert.assertEquals(book.getName(), bookJSON.getString("name"));
        Assert.assertEquals(book.getPrice(), bookJSON.getInt("price"));
        Assert.assertEquals(book.getIsbn(), bookJSON.getString("isbn"));
        Assert.assertEquals(book.getCreatedTime().getTime(), bookJSON.getLong("createdTime"));
    }

    @Test
    public void testDeserializeJSONToPublisher() throws Exception {
        JSONObject publisherJSON = new JSONObject();
        publisherJSON
                .put("companyName", "Taipei Company")
                .put("address", "Taipei")
                .put("tel", "02-1234-5678");

        String strPublisherJSON = publisherJSON.toString();
        Publisher publisher = mapper.readValue(strPublisherJSON, Publisher.class);

        Assert.assertEquals(publisherJSON.getString("companyName"), publisher.getCompanyName());
        Assert.assertEquals(publisherJSON.getString("address"), publisher.getAddress());
        Assert.assertEquals(publisherJSON.getString("tel"), publisher.getTel());
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Book {
        private String id;
        private String name;
//        @JsonIgnore
        private int price;
        private String isbn;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createdTime;
        @JsonUnwrapped
        private Publisher publisher;

        // 略過get與set方法


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public Date getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(Date createdTime) {
            this.createdTime = createdTime;
        }

        public Publisher getPublisher() {
            return publisher;
        }

        public void setPublisher(Publisher publisher) {
            this.publisher = publisher;
        }
    }
    private static class Publisher {
        private String companyName;
        private String address;
        private String tel;

        // 略過get與set方法

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }
        public void setTel(String tel) {
            this.tel = tel;
        }

    }

}
