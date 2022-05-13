package com.test.shopservice;

import com.test.shopservice.entity.Product;
import com.test.shopservice.repository.ProductRepository;
import com.test.shopservice.service.ProductService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void setup() {

        productService = new ProductService(productRepository);

        Product chicken = Product.builder()
                .id(1)
                .name("chicken")
                .price(Float.parseFloat("23.99"))
                .build();

        Mockito.when(productRepository.findById(1))
                .thenReturn(Optional.of(chicken));
        Mockito.when(productRepository.save(chicken)).thenReturn(chicken);
    }

    @Test
    public void whenValidGetID_ThenReturnProduct() {

        Product product = productService.getProduct(1);
        Assertions.assertThat(product.getName()).isEqualTo("chicken");
    }

    @Test
    public void whenSave_ThenCreateProduct() {

        Product product = productService.createProduct(productService.getProduct(1));
        Assertions.assertThat(product).isNotNull();
    }

}
