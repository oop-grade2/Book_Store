package com.mycompany.bookstore.service;

import com.mycompany.bookstore.dao.CategoryDAO;
import com.mycompany.bookstore.model.Category;

import java.util.List;

public class CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }


    public void addCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name can not be empty");
        }
        Category category = new Category(categoryName.trim());
        categoryDAO.addCategory(category);
    }
    
    public void updateCategory(int categoryId, String newCategoryName) {
        if (newCategoryName == null || newCategoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name can not be empty");
        }
        
        Category category = categoryDAO.getCategoryById(categoryId);
        
        if (category != null) {
            category.setCategoryName(newCategoryName.trim());
            categoryDAO.updateCategory(category);
        } else {
            throw new IllegalArgumentException("Category not found with ID: " + categoryId);
        }
    }


    public void deleteCategory(int categoryId) {
        categoryDAO.deleteCategory(categoryId);
    }


    public Category getCategoryById(int categoryId) {
        return categoryDAO.getCategoryById(categoryId);
    }


    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }
}
