package com.test.shopservice.service;

import com.test.shopservice.entity.Product;
import com.test.shopservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        Optional<Product> productDB = this.repository.findById(product.getId());

        if (productDB.isEmpty()) {
            return null;
        }

        Product getProduct = productDB.get();
        getProduct.setName(getProduct.getName());
        getProduct.setPrice(getProduct.getPrice());

        return this.repository.save(getProduct);
    }

    public Boolean deleteProduct(Integer id) {

        Optional<Product> product = this.repository.findById(id);

        if (product.isEmpty()) {
            return false;
        }

        this.repository.delete(product.get());
        return true;
    }


}
