package com.mycompany.bookstore.model;
import java.util.List;
import java.util.ArrayList;

public class SubCategory {
    private int subCategoryId;
    private String subCategoryName;
    
    private Category category; //many to one relationship 
    private List<Book> books ; //zero to many relationship
    
    public SubCategory()
    {         
        this.books = new ArrayList<>();
    }
    
    public SubCategory(String subCategoryName, Category category) {
        this();
        this.subCategoryName = subCategoryName;
        this.category = category;
    }
    
    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    } 
    
    
}
