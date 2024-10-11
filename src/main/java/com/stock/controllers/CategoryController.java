package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.CategoryApi;
import com.stock.model.CategoryDTO;
import com.stock.pages.CategoryPage;
import com.stock.pages.ProductPage;
import com.stock.services.ICategoryService;
import com.stock.utils.TextUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final ICategoryService categoryService;
    private final TextUtil textUtil;

    @Override
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {

        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categories);
    }



    @Override
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Integer id) {

        CategoryDTO categorydto = categoryService.getCategoryById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categorydto);
    }

    @Override
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO CategoryDTO) {

        CategoryDTO categoryDTO1 = categoryService.addCategory(CategoryDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryDTO1);
    }

    @Override
    public ResponseEntity<CategoryDTO> editCategory(@PathVariable("id") Integer id, @RequestBody CategoryDTO CategoryDTO) {

        CategoryDTO CategoryDTO1 = categoryService.editCategory(id, CategoryDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoryDTO1);
    }

    @Override
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer id) {

        String category_txt = textUtil.getMessage("category");
        categoryService.deleteCategory(id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(textUtil.getMessage("delete.validation",category_txt ,id));
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> getCategoriesByName(@RequestParam String name) {

        List<CategoryDTO> categories = categoryService.searchCategoryByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categories);
    }

    @Override
    public ResponseEntity<CategoryPage> searchForCategoriesByName(@RequestParam String input,
                                                                    @RequestParam("sort") String sortField,
                                                                    @RequestParam String order,
                                                                    @RequestParam("page") Integer pageNum,
                                                                    @RequestParam("per_page") Integer limitPerPage
    ) {

        Sort.Direction direction = Objects.equals(order, "asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        if (StringUtils.isBlank(sortField)) {
            sortField = "name";
        }
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(pageNum, limitPerPage)
                .withSort(sort);

        CategoryPage categoryPage = categoryService.searchForCategoriesByName(input, pageable);
        categoryPage.setPageIndex((long) pageNum);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryPage);
    }

    /*

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
