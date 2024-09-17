package com.stock.services;

import com.stock.model.CategoryDTO;
import com.stock.pages.CategoryPage;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ICategoryService {


    CategoryDTO getById(Long id);

    List<CategoryDTO> getAll();

    CategoryDTO add(CategoryDTO t);

    CategoryDTO edit(CategoryDTO t, Long id);

    void delete(Long l);

    CategoryPage getByPage(int page);

}
