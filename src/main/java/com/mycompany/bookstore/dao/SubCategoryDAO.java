package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.SubCategory;
import java.util.List;

public interface SubCategoryDAO {
    void addSubCategory(SubCategory subCategory);
    void updateSubCategory(SubCategory subCategory);
    void deleteSubCategory(int subCategoryId);
    SubCategory getSubCategoryById(int subCategoryId);
    List<SubCategory> getAllSubCategories();
    List<SubCategory> getSubCategoriesByCategoryId(int categoryId);
}
