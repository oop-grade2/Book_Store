package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.SubCategoryDAO;
import com.mycompany.bookstore.dao.CategoryDAO;
import com.mycompany.bookstore.model.SubCategory;
import com.mycompany.bookstore.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryDAOimp implements SubCategoryDAO {

    private Connection connection;
    private CategoryDAO categoryDAO;

    public SubCategoryDAOimp(Connection connection, CategoryDAO categoryDAO) {
        this.connection = connection;
        this.categoryDAO = categoryDAO;
    }

    @Override
    public void addSubCategory(SubCategory subCategory) {
        String sql = "INSERT INTO SubCategory (SubCategoryName, CategoryID) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, subCategory.getSubCategoryName());
            
            if (subCategory.getCategory() != null) {
                statement.setInt(2, subCategory.getCategory().getCategoryId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }

            statement.executeUpdate();

            ResultSet res = statement.getGeneratedKeys();
            if (res.next()) {
                subCategory.setSubCategoryId(res.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("Error! Please try again");
            e.printStackTrace();
        }
    }

    @Override
    public void updateSubCategory(SubCategory subCategory) {
        String sql = "UPDATE SubCategory SET SubCategoryName=?, CategoryID=? WHERE SubCategoryID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, subCategory.getSubCategoryName());
 
            if (subCategory.getCategory() != null) {
            statement.setInt(2, subCategory.getCategory().getCategoryId());
            } else {
            statement.setNull(2, Types.INTEGER);
            }

            statement.setInt(3, subCategory.getSubCategoryId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error! Please try again");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSubCategory(int subCategoryId) {
        String sql = "DELETE FROM SubCategory WHERE SubCategoryID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, subCategoryId);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error! Please try again");
            e.printStackTrace();
        }
    }

    @Override
    public SubCategory getSubCategoryById(int subCategoryId) {
        String sql = "SELECT * FROM SubCategory WHERE SubCategoryID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, subCategoryId);
            ResultSet res = statement.executeQuery();

            if (res.next()) {
                return convertToSubCategory(res);
            }

        } catch (SQLException e) {
            System.out.println("Error! Please try again");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<SubCategory> getAllSubCategories() {
        List<SubCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM SubCategory";

        try (Statement statement = connection.createStatement()) {

            ResultSet res = statement.executeQuery(sql);

            while (res.next()) {
                list.add(convertToSubCategory(res));
            }

        } catch (SQLException e) {
            System.out.println("Error! Please try again");
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<SubCategory> getSubCategoriesByCategoryId(int categoryId) {
        List<SubCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM SubCategory WHERE CategoryID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
              list.add(convertToSubCategory(res));
            }

        } catch (SQLException e) {
            System.out.println("Error! Cannot get subcategories by categoryId");
            e.printStackTrace();
        }
    
        return list;
    }
    
    private SubCategory convertToSubCategory(ResultSet res) throws SQLException {
        SubCategory subCategory = new SubCategory();
        
        subCategory.setSubCategoryId(res.getInt("SubCategoryID"));
        subCategory.setSubCategoryName(res.getString("SubCategoryName"));

        int categoryId = res.getInt("CategoryID");
        if (!res.wasNull()) {
            Category category = categoryDAO.getCategoryById(categoryId);
            subCategory.setCategory(category);
        }

        return subCategory;
    }
    
}

