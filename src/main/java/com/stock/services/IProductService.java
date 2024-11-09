package com.stock.services;

import com.stock.model.ProductDTO;
import com.stock.pages.ProductPage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    ProductDTO getProductById(Integer id);
    List<ProductDTO> getAllProducts();
    ProductDTO addProduct(ProductDTO t);
    ProductDTO editProduct(Integer id, ProductDTO t);
    void deleteProduct(Integer id);

    void deleteProductsById(List<Integer> idList);
    List<ProductDTO> searchProductByName(String name);
    ProductPage searchForProductsByAnyColumn(String name, Pageable pageable);

    }
