package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.Category;
import java.util.List;

public interface CategoryDAO {
    void addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(int categoryId);
    Category getCategoryById(int categoryId);
    List<Category> getAllCategories();
}
