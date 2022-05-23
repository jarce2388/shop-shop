package com.test.shopservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.test.shopservice.entity.Client;
import lombok.Data;

import java.util.List;

@Data
public class SaleDto {

    Integer id;
    @JsonIgnoreProperties({"saleList","phone","email"})
    private Client client;

    @JsonIgnoreProperties({"sale","hibernateLazyInitializer"})
    private List<SaleDetailDto> detailList;

    private Double totalSale;
}
