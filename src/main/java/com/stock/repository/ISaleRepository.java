package com.stock.repository;

import com.stock.model.SaleDTO;
import com.stock.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISaleRepository extends JpaRepository<Sale,Integer> {
  List<SaleDTO> findByDescription(String description);
}

