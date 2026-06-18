package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.exception.APIResponse;
import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(value = "pageNumber", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int pageNumber,
                                                            @RequestParam(value = "pageSize", required = false, defaultValue =  AppConstant.DEFAULT_PAGE_SIZE) int pageSize,
                                                            @RequestParam(value = "sortOrder", required = false, defaultValue =  AppConstant.DEFAULT_SORT_ORDER) String sortOrder,
                                                            @RequestParam(value = "sortBy", required = false, defaultValue =  AppConstant.DEFAULT_SORT_BY_CATEGORY) String sortBy){
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortOrder, sortBy);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody Category category){
        CategoryDTO createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long categoryId){
        String status = categoryService.deleteCategory(categoryId);
        APIResponse apiResponse = new APIResponse(status, true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody Category category,
                                                  @PathVariable Long categoryId){
        CategoryDTO savedCategory = categoryService.updateCategory(category, categoryId);
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }
}
