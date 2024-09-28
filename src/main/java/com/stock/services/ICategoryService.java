package com.stock.services;

import com.stock.model.CategoryDTO;
import com.stock.pages.CategoryPage;
import java.util.List;


public interface ICategoryService {


    CategoryDTO getCategoryById(Integer id);

    List<CategoryDTO> getAllCategories();

    CategoryDTO addCategory(CategoryDTO t);

    CategoryDTO editCategory(Integer id, CategoryDTO t);

    void deleteCategory(Integer id);

    CategoryPage getCategoriesByPage(int page);

    List<CategoryDTO> searchCategoryByName(String name);

}
