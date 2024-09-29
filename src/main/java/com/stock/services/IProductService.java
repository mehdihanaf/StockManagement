package com.stock.services;

import com.stock.model.ProductDTO;
import java.util.List;

public interface IProductService {
    ProductDTO getProductById(Integer id);
    List<ProductDTO> getAllProducts();
    ProductDTO addProduct(ProductDTO t);
    ProductDTO editProduct(Integer id, ProductDTO t);
    void deleteProduct(Integer id);
    List<ProductDTO> searchProductByName(String name);
}
