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
    List<ProductDTO> searchProductByName(String name);
    ProductPage findByProductNameStartsWith(String name, Pageable pageable);

    }
