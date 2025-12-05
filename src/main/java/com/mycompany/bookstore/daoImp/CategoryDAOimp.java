package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.CategoryDAO;
import com.mycompany.bookstore.model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CategoryDAOimp implements CategoryDAO{
    
    private Connection connection;

    public CategoryDAOimp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addCategory(Category category) {
        String sql = "INSERT INTO Category (CategoryName) VALUES (?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, category.getCategoryName());
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                category.setCategoryId(keys.getInt(1));
            }
            
        } catch (SQLException e) {
            System.out.println("Error! Please try again");
            e.printStackTrace();
        }
    }

    @Override
    public void updateCategory(Category category) {
        String sql = "UPDATE Category SET CategoryName=? WHERE CategoryID=?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category.getCategoryName());
            statement.setInt(2, category.getCategoryId());
            statement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error! Please try again");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategory(int categoryId) {
        String sql = "DELETE FROM Category WHERE CategoryID=?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error! Please try again");
            e.printStackTrace();
        }
    }

    @Override
    public Category getCategoryById(int categoryId) {
        String sql = "SELECT * FROM Category WHERE CategoryID=?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            ResultSet res = statement.executeQuery();
            
            if (res.next()) {
                return convertToCategory(res);
            }
            
        } catch (SQLException e) {
            System.out.println("Error! Please try again");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try (Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery(sql);
            while (res.next()) {
                categories.add(convertToCategory(res));
            }
        } catch (SQLException e) {
            System.out.println("Error! Please try again");
            e.printStackTrace();
        }
        return categories;
    }

    private Category convertToCategory(ResultSet res) throws SQLException {
        Category category = new Category();
        category.setCategoryId(res.getInt("CategoryID"));
        category.setCategoryName(res.getString("CategoryName"));
        return category;
    }
    
    
}
