package com.stock.pages;


import com.stock.model.ProductDTO;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ProductPage {

    private List<ProductDTO> products;
    private long totalCount;
    private long pageIndex;

    public ProductPage(List<ProductDTO> products, long totalCount) {
        this.products = products;
        this.totalCount = totalCount;
    }
}
