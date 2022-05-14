package com.test.shopservice.controller;

import com.test.shopservice.entity.Product;
import com.test.shopservice.service.ProductService;
import lombok.RequiredArgsConstructor;
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result) {

        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Product newProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct( @PathVariable("id") Integer id, @Valid @RequestBody Product product, BindingResult result) {

        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        product.setId(id);
        Product setProduct = productService.updateProduct(product);

        if(setProduct == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(setProduct);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {

        boolean removedProduct = productService.deleteProduct(id);

        if (!removedProduct) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok("Eliminado Satisfactoriamente");
    }
}
