package com.stock.pages;

import com.stock.model.CategoryDTO;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class CategoryPage {

    private final Page<CategoryDTO> categoryPage;

    public CategoryPage(Page<CategoryDTO> categoryPage) {
        this.categoryPage = categoryPage;
    }
}
