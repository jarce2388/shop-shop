package com.test.shopservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nombre")
    private String name;

    @Column(name="apellido")
    private String lastName;

    @Column(name="dni")
    private Integer dni;

    @Column(name="telefono")
    private Integer phone;

    @Column(name="correo")
    private String email;
}
