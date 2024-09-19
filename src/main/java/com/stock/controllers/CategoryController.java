package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.CategoryApi;
import com.stock.model.CategoryDTO;
import com.stock.pages.CategoryPage;
import com.stock.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final ICategoryService categoryService;

    @Override
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        var categoryListDTO = categoryService.getAll();
        return ResponseEntity.ok(categoryListDTO);
    }

    @Override
    public ResponseEntity<CategoryPage> categoriesPerPage(@RequestParam Integer page) {
        return ResponseEntity.ok(categoryService.getByPage(--page));
    }

    @Override
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Integer id) {
        var categoryDTO = categoryService.getById(id);
        return ResponseEntity.ok(categoryDTO);
    }

    @Override
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO CategoryDTO) {
        var categoryDTO = categoryService.add(CategoryDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryDTO);
    }

    @Override
    public ResponseEntity<CategoryDTO> editCategory(@PathVariable("id") Integer id, @RequestBody CategoryDTO categoryDTO) {
        var updatedCategoryDTO = categoryService.edit(id, categoryDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(updatedCategoryDTO);
    }

    @Override
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("category is deleted");
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> getCategoriesByName(@RequestParam String name) {
        var categoriesDTOList = categoryService.search(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoriesDTOList);
    }

}
