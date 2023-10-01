package com.dandelion.backend.services.impl;


import com.dandelion.backend.entities.Category;
import com.dandelion.backend.exceptions.ResourceAlreadyExistsException;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.dto.CategoryDTO;
import com.dandelion.backend.repositories.CategoryRepo;
import com.dandelion.backend.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category cate = modelMapper.map(categoryDTO, Category.class);

        Optional<Category> tempCate = categoryRepo.findByNameIgnoreCase(cate.getName());

        if (tempCate.isPresent()) {
            throw new ResourceAlreadyExistsException("Category already exist with id = " + tempCate.get().getId());
        }

        Category addedCate = categoryRepo.save(cate);

        return modelMapper.map(addedCate, CategoryDTO.class);
    }
    
    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {

        Category cate = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id = " + categoryId));

        cate.setName(categoryDTO.getName());

        Category updatedCate = categoryRepo.save(cate);

        return modelMapper.map(updatedCate, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {

        Category cate = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id = " + categoryId));

        categoryRepo.delete(cate);
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {

        Category cate = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id = " + categoryId));

        return modelMapper.map(cate, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {

        List<Category> categories = categoryRepo.findAll();

        List<CategoryDTO> cateDTOs = categories.stream().map((cat) -> modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());

        return cateDTOs;
    }


}
