package com.test.shopservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.test.shopservice.entity.Product;
import lombok.Data;

@Data
public class SaleDetailDto {

    private Integer id;
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Product product;


    private Integer quantity = 0;

    private Double total;
}
