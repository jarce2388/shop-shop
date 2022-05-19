package com.test.shopservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class ProductDto {

    @NotEmpty(message = "El campo nombre no debe ser vacío")
    private String name;

    @Positive(message = "El campo precio debe ser mayor que 0")
    @NotNull(message = "El campo precio no debe estar vacío")
    private Float price;
}
