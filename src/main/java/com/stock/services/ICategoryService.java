package com.stock.services;

import com.stock.model.CategoryDTO;
import com.stock.pages.CategoryPage;
import com.stock.pages.SalePage;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ICategoryService {


    CategoryDTO getCategoryById(Integer id);

    List<CategoryDTO> getAllCategories();

    CategoryDTO addCategory(CategoryDTO t);

    CategoryDTO editCategory(Integer id, CategoryDTO t);

    void deleteCategory(Integer id);

    void deleteCategoriesById(List<Integer> idList);

    List<CategoryDTO> searchCategoryByName(String name);

    CategoryPage searchForCategoriesByName(String name, Pageable pageable);
}
