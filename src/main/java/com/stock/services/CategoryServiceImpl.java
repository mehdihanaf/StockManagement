package com.stock.services;

import com.stock.exceptions.TMNotFoundException;
import com.stock.model.CategoryDTO;
import com.stock.models.Category;
import com.stock.pages.CategoryPage;
import com.stock.repository.ICategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

    private static final int MAX_PER_PAGE = 5;
    private final ICategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(ICategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoryDTO getCategoryById(Integer id) {

        log.info("Retrieve Category with id {}", id);
        return categoryRepository.findById(id)
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .orElseThrow(() -> new TMNotFoundException("category not found"));
    }

    @Override
    public List<CategoryDTO> getAllCategories() {

        log.info("Fetching all categories");
        List<CategoryDTO> listCategories =  categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        log.info("Fetched {} categories", listCategories.size());
        return listCategories;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {

        log.info("Adding a new category: {}", categoryDTO);
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        log.info("Category saved with ID: {}", savedCategory.getId());
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO editCategory(Integer id, CategoryDTO categoryDTO) {

        log.info("retrieve category to edit with id : {}", id);
        getCategoryById(id);
        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setId(id);
        Category savedCategory = categoryRepository.save(category);
        log.info("Category edited  with ID: {}", savedCategory.getId());
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer id) {

        log.info("Retrieve Category to Delete with id {}", id);
        getCategoryById(id);
        categoryRepository.deleteById(id);
        log.info(" Category Deleted with id {}", id);
    }

    public List<CategoryDTO> searchCategoryByName(String name) {

        log.info("Retrieve Category to lookup with name {}", name);
        List<CategoryDTO> listCategoriesByName =  categoryRepository.findByName(name).stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        log.info("list of  Categories {} with name {}",listCategoriesByName, name);
        return listCategoriesByName;
    }

    @Override
    public CategoryPage getCategoriesByPage(int page) {

        log.info("Retrieve number of page of Category with page {}", page);
        var categoryPage = categoryRepository.findAll(PageRequest.of(page, MAX_PER_PAGE));
        log.info("page of Categories {} in page {}",categoryPage, page);
        return new CategoryPage(categoryPage.map(category -> modelMapper.map(category, CategoryDTO.class)));
    }
}