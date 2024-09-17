package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.CategoryApi;
import com.stock.model.CategoryDTO;
import com.stock.pages.CategoryPage;
import com.stock.services.CategoryServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
public class CategoryController implements CategoryApi {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
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
/*
    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id) {

        CategoryDTO categorydto = categoryService.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categorydto);
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


    @PostMapping("/category")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO CategoryDTO) {

        CategoryDTO categoryDTO1 = categoryServiceI.add(CategoryDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryDTO1);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<CategoryDTO> editCategory(@RequestBody CategoryDTO CategoryDTO, @PathVariable("id") Long id) {

        CategoryDTO CategoryDTO1 = categoryServiceI.edit(CategoryDTO, id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoryDTO1);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {

        categoryServiceI.delete(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("category is deleted");
    }
*/

}
