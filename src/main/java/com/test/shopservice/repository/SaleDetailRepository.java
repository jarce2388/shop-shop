package com.test.shopservice.repository;

import com.test.shopservice.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {

    List<SaleDetail> findBySaleId(Integer id);

    //No puedo utilizar esta vaiante, debido a que no me reconoce el String de la Query
    //    @Query(value = "SELECT id FROM sale_detail AS DS INNER JOIN sale AS S ON(DS.id_sale=S.id) WHERE  S.id_client= :id")
    //    List<SaleDetail> findByClientId(Integer id);
}

