package com.test.shopservice.controller;

import com.test.shopservice.entity.Product;
import com.test.shopservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listProduct() {

        List<Product> listProduct = productService.listProduct();

        if (listProduct == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(listProduct);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Integer id) {

        Product product = productService.getProduct(id);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product, BindingResult result) {

        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Product newProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @PathVariable("id") Integer id, @RequestBody Product product) {

        product.setId(id);
        Product updatedProduct = productService.updateProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedProduct);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {

        boolean removedProduct = productService.deleteProduct(id);

        if (!removedProduct) {
            return new ResponseEntity<>("No existe", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Eliminado", HttpStatus.OK);
    }
}
