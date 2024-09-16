package com.StockManagement.repository;

import com.StockManagement.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISaleRepository extends JpaRepository<Sale,Long> {
}
