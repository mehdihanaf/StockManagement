package com.stock.repository;

import com.stock.model.SaleDTO;
import com.stock.models.Product;
import com.stock.models.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISaleRepository extends JpaRepository<Sale,Integer> {
  List<SaleDTO> findByDescription(String description);

  @Query(value = "SELECT s FROM Sale s WHERE lower(s.description) LIKE lower(?1)"
          + "OR lower(s.product.productName) LIKE lower(?1)"
          + "OR CAST(s.product.unitSellPrice AS string) LIKE lower(?1)"
          + "OR CAST(s.saleQuantity AS string) LIKE lower(?1)"
          + "OR CAST(s.saleDate AS string) LIKE lower(?1)"
          + "OR CAST(s.discount AS string) LIKE lower(?1)",
          countQuery = "SELECT count(*) FROM Sale s WHERE lower(s.description) LIKE lower(?1)"
                  + "OR lower(s.product.productName) LIKE lower(?1)"
                  + "OR CAST(s.product.unitSellPrice AS string) LIKE lower(?1)"
                  + "OR CAST(s.saleQuantity AS string) LIKE lower(?1)"
                  + "OR CAST(s.saleDate AS string) LIKE lower(?1)"
                  + "OR CAST(s.discount AS string) LIKE lower(?1)"
  )
  Page<Sale> searchForSalesByAnyColumn(String name, Pageable pageable);


}

