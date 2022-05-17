package com.test.shopservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "El campo NOMBRE no debe ser vacío")
    private String name;
    private String lastName;

    @Positive(message = "El campo DNI debe ser mayor que 0")
    @NotNull(message = "El campo DNI no debe ser nulo")
    private Integer dni;
    private Integer phone;

    @NotEmpty(message = "El campo EMAIL no debe ser vacío")
    private String email;
}
