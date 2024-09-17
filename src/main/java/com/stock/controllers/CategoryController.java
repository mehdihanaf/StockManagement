package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.CategoriesApi;
import com.stock.model.CategoryDTO;
import com.stock.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(StockManagementConstants.API_VERSION)
public class CategoryController implements CategoriesApi {


    private final ICategoryService categoryServiceI;





    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> allCategories() {

        List<CategoryDTO> categories = categoryServiceI.getAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categories);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id) {

        CategoryDTO categorydto = categoryServiceI.getById(id);
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
*/

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


}
