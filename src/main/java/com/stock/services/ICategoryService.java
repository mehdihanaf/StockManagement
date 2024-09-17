package com.stock.services;

import com.stock.model.CategoryDTO;

import java.util.List;


public interface ICategoryService {


    CategoryDTO getById(Long id);

    List<CategoryDTO> getAll();

    CategoryDTO add(CategoryDTO t);

    CategoryDTO edit(CategoryDTO t, Long id);

    void delete(Long l);

    List<CategoryDTO> getByPage(int page);

}
