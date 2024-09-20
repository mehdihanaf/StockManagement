package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.ProductApi;
import com.stock.model.ProductDTO;
import com.stock.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
public class ProductController implements ProductApi {

    private final IProductService productService;


    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Integer id) {

        ProductDTO productdto = productService.getProductById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productdto);
    }





    @Override
    public ResponseEntity<List<ProductDTO>> getAllProducts() {

        List<ProductDTO> listproducts = productService.getAllProducts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listproducts);
    }


    @Override
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {

        ProductDTO productDTO1 = productService.addProduct(productDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productDTO1);
    }

    @Override
    public ResponseEntity<ProductDTO> editProduct(@PathVariable("id") Integer id, @RequestBody ProductDTO productDTO) {

        ProductDTO productDTO1 = productService.editProduct(id, productDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productDTO1);
    }

    @Override
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {

        //todo validation
        productService.deleteProduct(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("product is deleted");
    }


    @Override
    public ResponseEntity<List<ProductDTO>> getProductsByName(@RequestParam String name) {

        List<ProductDTO> products = productService.searchProductByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(products);
    }

    /*
    @Override
    public ResponseEntity<ProductPage> ProductsPerPage(@RequestParam Integer page) {
        return ResponseEntity.ok(productService.getByPage(--page));
    }

*/


}
