package com.test.shopservice.repository;

import com.test.shopservice.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {
}

