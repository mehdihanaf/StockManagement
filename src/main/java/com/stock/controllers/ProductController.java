package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.ProductApi;
import com.stock.model.ProductDTO;
import com.stock.pages.ProductPage;
import com.stock.services.IProductService;
import com.stock.utils.PagingUtil;
import com.stock.utils.TextUtil;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final IProductService productService;
    private final TextUtil textUtil;


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
    public ResponseEntity<ProductPage> searchForProductsByAnyColumn(@RequestParam String input,
                                                              @RequestParam("sort") String sortField,
                                                              @RequestParam String order,
                                                              @RequestParam("page") Integer pageNum,
                                                              @RequestParam("per_page") Integer limitPerPage
    ) {

        Pageable pageable = PagingUtil.getPageable(sortField, "buyDate", order, pageNum, limitPerPage);

        ProductPage productPage = productService.searchForProductsByAnyColumn(input, pageable);
        productPage.setPageIndex((long) pageNum);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productPage);
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
    public ResponseEntity<Resource> exportProductsAsCSVFile(String input, String sortField, String order, Integer pageNum, Integer limitPerPage) {
        Pageable pageable = PagingUtil.getPageable(sortField, "buyDate", order, pageNum, limitPerPage);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String filename = "products" + LocalDateTime.now().format(formatter) + ".csv";
        Resource resource = productService.export(input, pageable);

        // Prepare response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }


    @Override
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("product is deleted");
    }

    @Override
    public ResponseEntity<String> deleteProductsById(List<Integer> idList) {
        String product_txt = textUtil.getMessage("products");
        productService.deleteProductsById(idList);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(textUtil.getMessage("deleteall.validation",product_txt));
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getProductsByName(@RequestParam String name) {

        List<ProductDTO> products = productService.searchProductByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(products);
    }

}
