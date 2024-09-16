package com.stock.repository;

import com.stock.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Sale,Long> {
}
