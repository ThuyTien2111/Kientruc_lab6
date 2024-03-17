package vn.edu.iuh.fit.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.productservice.dao.ProductDao;
import vn.edu.iuh.fit.productservice.entity.Order;
import vn.edu.iuh.fit.productservice.entity.Product;
import vn.edu.iuh.fit.productservice.entity.User;

@RestController
@RequestMapping("/")
public class ProductControll {
    private RestTemplate restTemplate;
    private ProductDao productDao;
    @Autowired
    public ProductControll(RestTemplate restTemplate, ProductDao productDao) {
        this.restTemplate = restTemplate;
        this.productDao = productDao;
    }
    @GetMapping("/order/{userId}/{productId}")
    public Order getUserProductById(@PathVariable Long userId, @PathVariable Long productId) {
        Product product=productDao.getProduct(productId);
        User user = restTemplate.getForObject("http://localhost:8001/user/2", User.class);
        if(user!= null){
            Order order=new Order(1, user.getUsername(), product.getProductname(), product.getPrice());
            return order;
        }else {
            return null;
        }

    }
    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product) {
        productDao.addProduct(product);
        return product;
    }
}
