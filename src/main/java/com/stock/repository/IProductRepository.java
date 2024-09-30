package com.stock.repository;

import com.stock.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByProductName(String name);

    @Query(value = "SELECT p FROM Product p WHERE lower(p.productName) LIKE lower(?1)",
            countQuery = "SELECT count(*) FROM Product p WHERE lower(p.productName) LIKE lower(?1)"
            )
    Page<Product> searchForProducts(String name, Pageable pageable);

}
