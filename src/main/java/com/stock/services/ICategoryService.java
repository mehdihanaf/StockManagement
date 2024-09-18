package com.stock.services;

import com.stock.model.CategoryDTO;
import com.stock.pages.CategoryPage;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ICategoryService {


    CategoryDTO getById(Integer id);

    List<CategoryDTO> getAll();

    CategoryDTO add(CategoryDTO t);

    CategoryDTO edit( Integer id, CategoryDTO t);

    void delete(Integer id);

    CategoryPage getByPage(int page);

    List<CategoryDTO> search(String name);

}
