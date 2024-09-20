package com.stock.pages;


import com.stock.model.ProductDTO;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ProductPage {

    private final Page<ProductDTO> productPage;

    public ProductPage(Page<ProductDTO> productpage) {
        this.productPage = productpage;
    }
}
