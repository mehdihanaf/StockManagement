package com.stock.pages;


import com.stock.model.ProductDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProductPage {

    private List<ProductDTO> products;
    private long totalCount;
    private long pageIndex;

}
