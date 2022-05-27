package com.test.shopservice.controller;

import com.test.shopservice.dto.ProductDto;
import com.test.shopservice.entity.Product;
import com.test.shopservice.exception.CustomBadRequestException;
import com.test.shopservice.exception.CustomNotFoundException;
import com.test.shopservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;
    public static final String MESSAGE_NOT_FOUND = "Producto no encontrado";

    private final ErrorLog errorLog = (httpStatus, httpMethod, message) -> {
        String msg = httpMethod.name() + " : " + httpStatus.name() + " : " + httpStatus.value() + " : " + message;
        Logger logger = LogManager.getLogger("product-log");
        logger.error(msg);
    };

    @GetMapping
    @Operation(tags = "Servicio Producto", summary = "Listar Producto.", description = "Lista Todos los productos existentes.")
    public ResponseEntity<List<Product>> listProduct() {

        List<Product> listProduct = productService.listProduct();
        if (listProduct == null) {
            errorLog.register(HttpStatus.NOT_FOUND, HttpMethod.GET, MESSAGE_NOT_FOUND);
            throw new CustomNotFoundException(MESSAGE_NOT_FOUND);
        }

        return ResponseEntity.ok(listProduct);
    }

    @GetMapping(value = "/{id}")
    @Operation(tags = "Servicio Producto", summary = "Obtener Producto.", description = "Devuelve un Producto dado un Id válido.")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Integer id) {

        Product product = productService.getProduct(id);
        if (product == null) {
            errorLog.register(HttpStatus.NOT_FOUND, HttpMethod.GET, MESSAGE_NOT_FOUND);
            throw new CustomNotFoundException(MESSAGE_NOT_FOUND);
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping
    @PreAuthorize("hasRole('app-admin')")
    @Operation(tags = "Servicio Producto", summary = "Crear Producto.", description = "Crea un nuevo Producto.")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDto productDto, BindingResult result) {

        if (result.hasErrors()) {
            errorLog.register(HttpStatus.BAD_REQUEST, HttpMethod.POST, this.toStringMessage(result));
            throw new CustomBadRequestException(this.toStringMessage(result));
        }
        Product newProduct = productService.createProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('app-admin')")
    @Operation(tags = "Servicio Producto", summary = "Actualizar Producto.", description = "Actualiza un Producto dado su Id.")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id, @Valid @RequestBody ProductDto productDto, BindingResult result) {

        if (result.hasErrors()) {
            errorLog.register(HttpStatus.BAD_REQUEST, HttpMethod.PUT, this.toStringMessage(result));
            throw new CustomBadRequestException(this.toStringMessage(result));
        }
        productDto.setId(id);
        Product updProduct = productService.updateProduct(productDto);
        if (updProduct == null) {
            errorLog.register(HttpStatus.NOT_FOUND, HttpMethod.PUT, MESSAGE_NOT_FOUND);
            throw new CustomNotFoundException(MESSAGE_NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(updProduct);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('app-admin')")
    @Operation(tags = "Servicio Producto", summary = "Eliminar Producto.", description = "Elimina un Producto dado su Id.")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {

        boolean removedProduct = productService.deleteProduct(id);
        if (!removedProduct) {
            errorLog.register(HttpStatus.NOT_FOUND, HttpMethod.DELETE, MESSAGE_NOT_FOUND);
            throw new CustomNotFoundException(MESSAGE_NOT_FOUND);
        }

        return ResponseEntity.ok("Eliminado");
    }

    private String toStringMessage(BindingResult result) {

        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        return errors.toString();
    }
}
