package com.stock.services;

import com.stock.exceptions.TMNotFoundException;
import com.stock.model.CategoryDTO;
import com.stock.models.Category;
import com.stock.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements ICategoryService{


    private static final int MAX_PER_PAGE = 5;

    private final ICategoryRepository categoryRepository;

    private final ModelMapper modelMapper;


    @Override
    public CategoryDTO getById(Long id) {

        return categoryRepository.findById(id)
                .map( category -> modelMapper.map(category, CategoryDTO.class))
                .orElseThrow(() -> new TMNotFoundException("category not found"));

    }

    @Override
    public List<CategoryDTO> getAll() {

        return categoryRepository.findAll().stream()
                                  .map( category -> modelMapper.map(category, CategoryDTO.class))
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
    public CategoryDTO edit(CategoryDTO categoryDTO, Long id) {

         getById(id);

        //TODO validations..

        Category category = modelMapper.map(categoryDTO,Category.class);

        category.setId(id);

        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory,CategoryDTO.class);
    }

    @Override
    public void delete(Long id) {

        //TODO validations...

        categoryRepository.deleteById(id);

    }

    @Override
    public List<CategoryDTO> getByPage(int page) {

        Page<Category> pageCategories = this.categoryRepository.findAll(PageRequest.of(page, MAX_PER_PAGE));

        return pageCategories.getContent().stream()
                                          .map(category -> modelMapper.map(category,CategoryDTO.class))
                                          .collect(Collectors.toList());

    }




}
