package com.test.shopservice;

import com.test.shopservice.controller.ProductController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopServiceApplicationTests {

    @Autowired
    private ProductController productController;

    @Test
    void contextLoads() {
        Assertions.assertThat(productController).isNotNull();
    }

}
