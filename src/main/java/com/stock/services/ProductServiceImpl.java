package com.stock.services;

import com.stock.exceptions.CustomResponseException;
import com.stock.exceptions.TMNotFoundException;
import com.stock.model.CategoryDTO;
import com.stock.model.ProductDTO;
import com.stock.models.Product;
import com.stock.pages.CategoryPage;
import com.stock.pages.ProductPage;
import com.stock.repository.IProductRepository;
import com.stock.utils.CsvUtil;
import com.stock.utils.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final ICategoryService categoryService;
    private final ModelMapper modelMapper;
    private final TextUtil textUtil;

    public ProductServiceImpl(IProductRepository productRepository, ICategoryService categoryService, ModelMapper modelMapper, TextUtil textUtil) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.textUtil = textUtil;
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        log.info("Product product with id {}", id);
        var product_txt = textUtil.getMessage("product");
        return productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .orElseThrow(() -> new TMNotFoundException(textUtil.getMessage("error.notfound",product_txt, id)));

    }

    public List<ProductDTO> getAllProducts() {
        log.info("Fetching all products");
        List<ProductDTO> listProducts = productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        log.info("Fetched {} products", listProducts.size());
        return listProducts;
    }
    @Override
    public ProductPage searchForProductsByAnyColumn(String name, Pageable pageable) {
        Page<Product> productPage = productRepository.searchForProductsByAnyColumn( "%"+ name+"%", pageable);
        Page<ProductDTO> productDtoPage = productPage.map(product -> modelMapper.map(product, ProductDTO.class));
        ProductPage pPage = new ProductPage();
        pPage.setProducts(productDtoPage.getContent());
        pPage.setTotalCount(productPage.getTotalElements());
        return pPage;
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(productDTO.getCategory().getId());
        productDTO.setCategory(categoryDTO);
        log.info("Adding a new product: {}", productDTO);
        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        log.info("product saved with ID: {}", savedProduct.getId());
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO editProduct(Integer id, ProductDTO productDTO) {
        log.info("retrieve product {} to edit with id : {}", productDTO, id);
        getProductById(id);
        Product product = modelMapper.map(productDTO, Product.class);
        product.setId(id);
        Product savedProduct = productRepository.save(product);
        log.info("product edited  with ID: {}", savedProduct.getId());
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public void deleteProduct(Integer id) {
        log.info("Retrieve product to Delete with id {}", id);
        getProductById(id);
        productRepository.deleteById(id);
        log.info(" product Deleted with id {}", id);
    }

    @Override
    public void deleteProductsById(List<Integer> idList) {

        try {
            productRepository.deleteAllById(idList);
        }catch(DataIntegrityViolationException ex){
            log.info("category deletion exception ");
            throw new CustomResponseException(textUtil.getMessage("error.product.assosciate"));
        }


    }

    @Override
    public List<ProductDTO> searchProductByName(String name) {
        log.info("Retrieve product to lookup with name {}", name);
        List<ProductDTO> listProductsByName = productRepository.searchProductByProductName("%" +name+ "%").stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        log.info("list of  Products {} with name {}", listProductsByName, name);
        return listProductsByName;
    }

    @Override
    public Resource export(String name, Pageable pageable){
        ProductPage productPage = searchForProductsByAnyColumn(name, pageable);
        return CsvUtil.export(productPage.getProducts());
    }
}





