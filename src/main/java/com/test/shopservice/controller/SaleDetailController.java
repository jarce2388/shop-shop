package com.test.shopservice.controller;

import com.test.shopservice.exception.CustomNotFoundException;
import com.test.shopservice.service.SaleDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "details")
public class SaleDetailController {

    private final SaleDetailService service;

    @GetMapping(value = {"/sale/{idSale}", "/client/{idClient}"})
    public Mono<ResponseEntity<Object[]>> getDetailBySale(@PathVariable Map<String, String> pathMap) {

        Mono<Object[]> detail = null;

        Optional<String> obj = pathMap.keySet().stream().findAny();
        String key = !obj.isEmpty() ? obj.get() : "";
        Integer value = !obj.isEmpty() ? Integer.parseInt(obj.get()) : 0;

        switch (key) {

            case "idSale":
                detail = this.service.findBySaleId(value);
                break;

            case "idClient":
                detail = this.service.findByClientId(value);
                break;

            default:
                break;

        }
        if (detail == null) {
            throw new CustomNotFoundException("Id no existe");
        }

        return detail.map(element -> ResponseEntity.ok(element));
    }

}
