package com.stock.services;

import com.stock.exceptions.TMNotFoundException;
import com.stock.model.CategoryDTO;
import com.stock.model.ProductDTO;
import com.stock.models.Product;
import com.stock.repository.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final ICategoryService categoryService;
    private final ModelMapper modelMapper;


    public ProductServiceImpl(IProductRepository productRepository, ICategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO getProductById(Integer id) {

        log.info("Product product with id {}", id);

        return productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .orElseThrow(() -> new TMNotFoundException("product not found"));

    }

    public List<ProductDTO> getAll() {

        log.info("Fetching all products");

        List<ProductDTO> listProducts = productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        log.info("Fetched {} products", listProducts.size());

        return listProducts;


    }

    @Override
    public ProductDTO add(ProductDTO productDTO) {

        CategoryDTO categoryDTO = categoryService.getById(productDTO.getCategory().getId());
        productDTO.setCategory(categoryDTO);
        log.info("Adding a new product: {}", productDTO);
        //TODO validations..
        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        log.info("product saved with ID: {}", savedProduct.getId());
        return modelMapper.map(savedProduct, ProductDTO.class);

    }

    @Override
    public ProductDTO edit(Integer id, ProductDTO productDTO) {

        log.info("retrieve product {} to edit with id : {}", productDTO, id);
        //trigger exception if id not exist
        getProductById(id);  //productDTO
        //TODO validations..
        Product product = modelMapper.map(productDTO, Product.class);
        product.setId(id);
        Product savedProduct = productRepository.save(product);
        log.info("product edited  with ID: {}", savedProduct.getId());
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public void delete(Integer id) {

        log.info("Retrieve product to Delete with id {}", id);

        getProductById(id);
        //TODO validations...
        productRepository.deleteById(id);

        log.info(" product Deleted with id {}", id);

    }

    @Override
    public List<ProductDTO> search(String name) {

        log.info("Retrieve product to lookup with name {}", name);

        List<ProductDTO> listProductsByName = productRepository.findByProductName(name).stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        log.info("list of  Categories {} with name {}", listProductsByName, name);

        return listProductsByName;


    }
}





