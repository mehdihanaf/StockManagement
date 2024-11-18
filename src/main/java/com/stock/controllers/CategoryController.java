package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.CategoryApi;
import com.stock.model.CategoryDTO;
import com.stock.pages.CategoryPage;
import com.stock.pages.ProductPage;
import com.stock.services.CategoryServiceImpl;
import com.stock.services.ICategoryService;
import com.stock.utils.TextUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final ICategoryService categoryService;
    private final TextUtil textUtil;
    private final CategoryServiceImpl categoryServiceImpl;

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
    public ResponseEntity<Resource> exportCSVFile(
        @NotNull  @Valid @RequestParam(value = "input", required = true) String input,
        @NotNull  @Valid @RequestParam(value = "sort", required = true) String sortField,
        @NotNull @Valid @RequestParam(value = "order", required = true) String order,
        @NotNull  @Valid @RequestParam(value = "page", required = true, defaultValue = "1") Integer pageNum,
        @NotNull @Min(1)  @Valid @RequestParam(value = "per_page", required = true) Integer limitPerPage
    ) {

        Sort.Direction direction = Objects.equals(order, "asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        if (StringUtils.isBlank(sortField)) {
            sortField = "name";
        }
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(pageNum, limitPerPage)
                .withSort(sort);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String filename = "categories" + LocalDateTime.now().format(formatter) + ".csv";
        Resource resource = categoryServiceImpl.export(input, pageable);

        // Prepare response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\"");


        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
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
    public ResponseEntity<String> deleteCategoriesById(List<Integer> idList) {
        String category_txt = textUtil.getMessage("categories");
         categoryService.deleteCategoriesById(idList);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(textUtil.getMessage("deleteall.validation",category_txt));
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



}
