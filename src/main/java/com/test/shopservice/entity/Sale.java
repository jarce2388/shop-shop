package com.test.shopservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client")
    @JsonIgnoreProperties({"saleList","phone","email"})
    private Client client;

    @OneToMany(mappedBy = "sale")
    @JsonIgnoreProperties("sale")
    private List<SaleDetail> detailList;



}
