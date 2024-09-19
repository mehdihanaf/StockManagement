package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.ProductApi;
import com.stock.model.ProductDTO;
import com.stock.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
public class ProductController implements ProductApi {

    private final IProductService productService;


    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Integer id) {

        ProductDTO productdto = productService.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productdto);
    }




    /*
    @Override
    public ResponseEntity<List<ProductDTO>> getAllProducts() {

        List<ProductDTO> products = productService.getAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(products);
    }
*/











}
