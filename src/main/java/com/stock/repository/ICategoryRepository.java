package com.stock.repository;

import com.stock.models.Category;
import com.stock.models.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Integer> {

    List<Category>  findByName(String name);


    @Query(value = "SELECT c FROM Category c WHERE lower(c.name) LIKE lower(?1)",
            countQuery = "SELECT count(*) FROM Category c WHERE lower(c.name) LIKE lower(?1)"
    )
    Page<Category> searchForCategoriesByName(String name, Pageable pageable);




}
