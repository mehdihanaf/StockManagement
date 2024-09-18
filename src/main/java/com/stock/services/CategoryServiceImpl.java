package com.stock.services;

import com.stock.exceptions.TMNotFoundException;
import com.stock.model.CategoryDTO;
import com.stock.models.Category;
import com.stock.pages.CategoryPage;
import com.stock.repository.ICategoryRepository;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private static final int MAX_PER_PAGE = 5;

    private final ICategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    public CategoryServiceImpl(ICategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoryDTO getById(Integer id) {
        return categoryRepository.findById(id)
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .orElseThrow(() -> new TMNotFoundException("category not found"));
    }

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO add(CategoryDTO categoryDTO) {

        //TODO validations..

        // Map DTO to Entity
        Category category = modelMapper.map(categoryDTO, Category.class);

        // Save the entity
        Category savedCategory = categoryRepository.save(category);

        // Map saved entity back to DTO
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO edit(Integer id, CategoryDTO categoryDTO) {

        getById(id);


        //TODO validations..


        Category category = modelMapper.map(categoryDTO, Category.class);

        category.setId(id);

        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public void delete( Integer id) {

        //TODO validations...
        categoryRepository.deleteById(id);

    }

    public List<CategoryDTO> search(String name) {

        return categoryRepository.findByName(name).stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public CategoryPage getByPage(int page) {
        var categoryPage = categoryRepository.findAll(PageRequest.of(page, MAX_PER_PAGE));
        return new CategoryPage(categoryPage.map(category -> modelMapper.map(category, CategoryDTO.class)));
    }

}
