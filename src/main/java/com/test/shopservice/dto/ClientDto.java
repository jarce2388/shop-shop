package com.test.shopservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class ClientDto {

    private Integer id;
    @NotEmpty(message = "El campo NOMBRE no debe ser vacío")
    private String name;

    @NotEmpty(message = "El campo NOMBRE no debe ser vacío")
    @Column(name = "last_name")
    private String lastName;

    @Positive(message = "El campo DNI debe ser mayor que 0")
    @NotNull(message = "El campo DNI no debe ser nulo")
    private Integer dni;

    private Integer phone;
    private String email;
}
