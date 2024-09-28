package com.stock.services;

import com.stock.exceptions.TMNotFoundException;
import com.stock.model.CategoryDTO;
import com.stock.model.ProductDTO;
import com.stock.pages.CategoryPage;
import com.stock.pages.ProductPage;
import com.stock.repository.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService{

    private final IProductRepository productRepository;
    private final ModelMapper modelMapper;
    private static final int MAX_PER_PAGE = 5;


    public ProductServiceImpl(IProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO getById(Integer id) {

        log.info("Product Category with id {}", id);

        return productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .orElseThrow(() -> new TMNotFoundException("product not found"));

    }

    public List<ProductDTO> getAll(){

        return null;
    }
    @Override
    public ProductDTO add(ProductDTO t) {
        return null;
    }

    @Override
    public ProductDTO edit(Integer id, ProductDTO t) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public ProductPage getByPage(int page) {
        var categoryPage = productRepository.findAll(PageRequest.of(page, MAX_PER_PAGE));
        return new ProductPage(categoryPage.map(product -> modelMapper.map(product, ProductDTO.class)));
    }

    @Override
    public List<ProductDTO> search(String name) {
        return null;
    }
}





