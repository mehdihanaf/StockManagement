package com.stock.repository;

import com.stock.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Integer> {


    @Query(value = "SELECT p FROM Product p WHERE lower(p.productName) LIKE lower(?1)")
    List<Product> searchProductByProductName(String name);

    @Query(value = "SELECT p FROM Product p WHERE lower(p.productName) LIKE lower(?1) " +
            "OR lower(p.productCode) LIKE lower(?1) OR CAST(p.quantity AS string) LIKE lower(?1)"
            + "OR CAST(p.unitBuyPrice AS string) LIKE lower(?1) OR CAST(p.unitSellPrice AS string) LIKE lower(?1)"
            + "OR CAST(p.buyDate AS string) LIKE lower(?1) OR lower(p.category.name) LIKE lower(?1)",
            countQuery = "SELECT count(*) FROM Product p WHERE lower(p.productName) LIKE lower(?1)"
            +"OR lower(p.productCode) LIKE lower(?1) OR CAST(p.quantity AS string) LIKE lower(?1)"
            +"OR CAST(p.unitBuyPrice AS string) LIKE lower(?1) OR CAST(p.unitSellPrice AS string) LIKE lower(?1)"
            +"OR CAST(p.buyDate AS string) LIKE lower(?1) OR lower(p.category.name) LIKE lower(?1)"
            )
    Page<Product> searchForProductsByAnyColumn(String name, Pageable pageable);

}
