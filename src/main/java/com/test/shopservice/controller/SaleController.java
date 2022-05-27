package com.test.shopservice.controller;

import com.test.shopservice.dto.SaleDto;
import com.test.shopservice.entity.SaleDetail;
import com.test.shopservice.exception.CustomBadRequestException;
import com.test.shopservice.exception.CustomNotFoundException;
import com.test.shopservice.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = "sales")
public class SaleController {

    private final SaleService saleService;

    private final ErrorLog errorLog = (httpStatus, httpMethod, message) -> {
        String msg = httpMethod.name() + " : " + httpStatus.name() + " : " + httpStatus.value() + " : " + message;
        Logger logger = LogManager.getLogger("sale-log");
        logger.error(msg);
    };

    @GetMapping
    @Operation(tags = "Servicio Venta", summary = "Listar Ventas.", description = "Lista Todas las Ventas existentes.")
    public ResponseEntity<List<SaleDto>> listSale() {
        return ResponseEntity.ok(this.saleService.listSale());
    }

    @GetMapping(value = "/{id}")
    @Operation(tags = "Servicio Venta", summary = "Obtener Ventas.", description = "Obtiene una Venta, dado su ID.")
    public ResponseEntity<SaleDto> getSale(@PathVariable("id") Integer id) {

        SaleDto sale = this.saleService.getSale(id);
        if (sale == null) {
            errorLog.register(HttpStatus.NOT_FOUND, HttpMethod.GET, "Id no existe");
            throw new CustomNotFoundException("Id no existe");
        }

        return ResponseEntity.ok(sale);
    }

    @PostMapping
    @PreAuthorize("hasRole('app-admin')")
    @Operation(tags = "Servicio Venta", summary = "Crear Venta.", description = "Crea una nueva Venta.")
    public ResponseEntity<List<SaleDetail>> createSale(@Valid @RequestBody SaleDto saleDto, BindingResult result) {

        if (result.hasErrors()) {
            errorLog.register(HttpStatus.BAD_REQUEST, HttpMethod.POST, this.toStringMessage(result));
            throw new CustomBadRequestException(this.toStringMessage(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.saleService.createSale(saleDto));
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
