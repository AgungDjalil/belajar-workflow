package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(int pageNumber, int pageSize, String sortOrder, String sortBy);
    CategoryDTO createCategory(Category category);

    String deleteCategory(Long categoryId);

    CategoryDTO updateCategory(Category category, Long categoryId);
}
