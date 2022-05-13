package com.test.shopservice.service;

import com.test.shopservice.entity.Product;
import com.test.shopservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> listProduct() {
        return this.repository.findAll();
    }

    public Product getProduct(Integer id) {
        return this.repository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {

        return this.repository.save(product);
    }

    public Product updateProduct(Product product) {

        Product productDB = this.repository.getById(product.getId());

        productDB.setName(product.getName());
        productDB.setPrice(product.getPrice());

        return this.repository.save(productDB);
    }

    public boolean deleteProduct(Integer id) {

        Product product = this.repository.getById(id);

        if( product == null ){
            return false;
        }

        this.repository.delete(product);

        return true;
    }


}
