package com.stock.pages;

import com.stock.model.CategoryDTO;
import lombok.Data;


import java.util.List;

@Data
public class CategoryPage {

    private List<CategoryDTO> categories;
    private long totalCount;
    private long pageIndex;
}
