package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.CategoryApi;
import com.stock.model.CategoryDTO;
import com.stock.pages.CategoryPage;
import com.stock.services.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
public class CategoryController implements CategoryApi {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {

        List<CategoryDTO> categories = categoryService.getAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categories);
    }

    @Override
    public ResponseEntity<CategoryPage> categoriesPerPage(@RequestParam Integer page) {
        return ResponseEntity.ok(categoryService.getByPage(--page));
    }

    @Override
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Integer id) {

        CategoryDTO categorydto = categoryService.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categorydto);
    }

    @Override
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO CategoryDTO) {

        CategoryDTO categoryDTO1 = categoryService.add(CategoryDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryDTO1);
    }

    @Override
    public ResponseEntity<CategoryDTO> editCategory(@PathVariable("id") Integer id, @RequestBody CategoryDTO CategoryDTO) {

        CategoryDTO CategoryDTO1 = categoryService.edit(id, CategoryDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoryDTO1);
    }

    @Override
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer id) {

        //todo validation
        categoryService.delete(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("category is deleted");
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> getCategoriesByName(@RequestParam String name) {

        List<CategoryDTO> categories = categoryService.search(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categories);
    }

    /*
     //TODO...
     makatreturnish list
    @GetMapping("/categories/page/{id}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByPage(@PathVariable("id") int id) {

        List<CategoryDTO> categoriesByPage = categoryServiceI.getByPage(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoriesByPage);
    }



*/

}
