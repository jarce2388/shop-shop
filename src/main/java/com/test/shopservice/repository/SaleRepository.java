package com.test.shopservice.repository;

import com.test.shopservice.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface SaleRepository extends JpaRepository<Sale, Integer> {

}
