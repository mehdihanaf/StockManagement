package com.stock.services;

import com.stock.model.CategoryDTO;
import com.stock.pages.CategoryPage;
import com.stock.pages.SalePage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.IOException;
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

    Resource export(String name, Pageable pageable);

}
