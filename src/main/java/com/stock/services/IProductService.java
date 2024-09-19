package com.stock.services;

import com.stock.model.ProductDTO;
import java.util.List;

public interface IProductService {


    ProductDTO getProductById(Integer id);

    List<ProductDTO> getAll();

    ProductDTO add(ProductDTO t);

    ProductDTO edit( Integer id, ProductDTO t);

    void delete(Integer id);

    //ProductPage getByPage(int page);

    List<ProductDTO> search(String name);
    
    
    
}
