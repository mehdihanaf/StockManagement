package com.stock.services.impl;

import com.stock.exceptions.CustomResponseException;
import com.stock.exceptions.TMNotFoundException;
import com.stock.model.CategoryDTO;
import com.stock.models.Category;
import com.stock.pages.CategoryPage;
import com.stock.repository.ICategoryRepository;
import com.stock.services.ICategoryService;
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
public class CategoryServiceImpl implements ICategoryService {

    private static final int MAX_PER_PAGE = 5;
    private final ICategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final TextUtil textUtil;

    public CategoryServiceImpl(ICategoryRepository categoryRepository, ModelMapper modelMapper, TextUtil textUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.textUtil = textUtil;
    }


    @Override
    public CategoryDTO getCategoryById(Integer id) {

        log.info("Retrieve Category with id {}", id);
        String category_txt = textUtil.getMessage("category");
        return categoryRepository.findById(id)
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .orElseThrow(() -> new TMNotFoundException(textUtil.getMessage("error.notfound",category_txt ,id)));
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
        try {
            categoryRepository.deleteById(id);
            log.info(" Category Deleted with id {}", id);
        }catch(DataIntegrityViolationException ex){
            log.info("category deletion exception ");
            throw new CustomResponseException(textUtil.getMessage("error.category.assosciate"));
        }
    }

    @Override
    public void deleteCategoriesById(List<Integer> idList) {
        try {
            categoryRepository.deleteAllById(idList);
        }catch(DataIntegrityViolationException ex){
            log.info("category deletion exception ");
            throw new CustomResponseException(textUtil.getMessage("error.category.assosciate"));
        }

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
    public CategoryPage searchForCategoriesByName(String name, Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.searchForCategoriesByName( "%"+ name+"%", pageable);
        log.info("this is category page");
        log.info(String.valueOf(categoryPage));
        Page<CategoryDTO> categoryDtoPage = categoryPage.map(category -> modelMapper.map(category, CategoryDTO.class));
        CategoryPage cPage = new CategoryPage();
        cPage.setCategories(categoryDtoPage.getContent());
        cPage.setTotalCount(categoryPage.getTotalElements());

        return cPage;
    }

    @Override
    public Resource export(String name, Pageable pageable){
        CategoryPage categoryPage = searchForCategoriesByName(name, pageable);
        return CsvUtil.export(categoryPage.getCategories());
    }


}
