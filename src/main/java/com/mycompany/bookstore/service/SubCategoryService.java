package com.mycompany.bookstore.service;

import com.mycompany.bookstore.dao.SubCategoryDAO;
import com.mycompany.bookstore.model.SubCategory;
import java.util.List;

public class SubCategoryService {

    private SubCategoryDAO subCategoryDAO;

    public SubCategoryService(SubCategoryDAO subCategoryDAO) {
        this.subCategoryDAO = subCategoryDAO;
    }


    public void addSubCategory(SubCategory subCategory) {

        if (subCategory == null)
            throw new IllegalArgumentException("SubCategory cannot be null");

        if (subCategory.getSubCategoryName() == null 
            || subCategory.getSubCategoryName().isBlank())
            throw new IllegalArgumentException("SubCategory name is required");

        if (subCategory.getCategory() == null 
            || subCategory.getCategory().getCategoryId() <= 0)
            throw new IllegalArgumentException("SubCategory must belong to a valid Category");

        subCategoryDAO.addSubCategory(subCategory);
    }

    public void updateSubCategory(SubCategory subCategory) {

        if (subCategory == null)
            throw new IllegalArgumentException("SubCategory cannot be null");

        if (subCategory.getSubCategoryId() <= 0)
            throw new IllegalArgumentException("SubCategory must have a valid id");

        subCategoryDAO.updateSubCategory(subCategory);
    }


    public void deleteSubCategory(int subCategoryId) {

        if (subCategoryId <= 0)
            throw new IllegalArgumentException("SubCategory id must be > 0");

        subCategoryDAO.deleteSubCategory(subCategoryId);
    }


    public SubCategory getSubCategoryById(int subCategoryId) {

        if (subCategoryId <= 0)
            throw new IllegalArgumentException("SubCategory id must be > 0");

        return subCategoryDAO.getSubCategoryById(subCategoryId);
    }

    public List<SubCategory> getAllSubCategories() {
        return subCategoryDAO.getAllSubCategories();
    }

    public List<SubCategory> getSubCategoriesByCategoryId(int categoryId) {

        if (categoryId <= 0)
            throw new IllegalArgumentException("Category id must be > 0");

        return subCategoryDAO.getSubCategoriesByCategoryId(categoryId);
    }
}
