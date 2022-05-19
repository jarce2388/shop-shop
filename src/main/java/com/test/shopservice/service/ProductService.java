package com.test.shopservice.service;

import com.test.shopservice.dto.ProductDto;
import com.test.shopservice.entity.Product;
import com.test.shopservice.exception.CustomNotFoundException;
import com.test.shopservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    @Autowired
    private ModelMapper modelMapper;


    public List<Product> listProduct() {
        return this.repository.findAll();
    }

    public Product getProduct(Integer id) {

        return this.repository.findById(id).orElse(null);
    }

    public Product createProduct(ProductDto productDto) {

        return this.repository.save(modelMapper.map(productDto,Product.class));
    }

    public Product updateProduct(ProductDto productDto) {

        Optional<Product> productDB = this.repository.findById(productDto.getId());

        if (productDB.isEmpty()) {
            return null;
        }

        Product newProduct = modelMapper.map(productDto, Product.class);
        return this.repository.save(newProduct);
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
