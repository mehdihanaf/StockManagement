package com.stock.services;

import com.stock.model.ProductDTO;
import com.stock.pages.ProductPage;

import java.util.List;

public interface IProductService {


    ProductDTO getById(Integer id);

    List<ProductDTO> getAll();

    ProductDTO add(ProductDTO t);

    ProductDTO edit( Integer id, ProductDTO t);

    void delete(Integer id);

    ProductPage getByPage(int page);

    List<ProductDTO> search(String name);
    
    
    
}
