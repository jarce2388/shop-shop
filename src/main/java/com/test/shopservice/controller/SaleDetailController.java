package com.test.shopservice.controller;

import com.test.shopservice.exception.CustomNotFoundException;
import com.test.shopservice.service.SaleDetailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "details")
public class SaleDetailController {

    private final SaleDetailService service;

    @Operation(tags = "Servicio Detalle_Venta", summary = "Obtener Detalles de Venta por ID de venta.", description = "Obtiene los detalles de una venta, dado el Id de venta.")
    public Mono<ResponseEntity<Object[]>> getDetailBySale(@PathVariable("id") Integer id) {

        Mono<Object[]> detail = this.service.findBySaleId(id);
        if (detail == null) {
            throw new CustomNotFoundException("Id no existe");
        }
        return detail.map(element -> ResponseEntity.ok(element));
    }

    @GetMapping(value = "/client/{id}")
    @Operation(tags = "Servicio Detalle_Venta", summary = "Obtener Detalles de Venta por ID de Cliente.", description = "Obtiene los detalles de venta de un Cliente.")
    public Mono<ResponseEntity<Object[]>> getDetailByClient(@PathVariable("id") Integer id) {

        Mono<Object[]> detail = this.service.findByClientId(id);
        if (detail == null) {
            throw new CustomNotFoundException("Id no existe");
        }

        return detail.map(element -> ResponseEntity.ok(element));
    }
}
