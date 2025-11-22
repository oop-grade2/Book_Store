/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bookstore.daoImp;
import com.mycompany.bookstore.dao.BookDAO;
import com.mycompany.bookstore.model.Book;
import java.sql.*;
/**
 *
 * @author Pc
 */
public class BookDAOimp implements BookDAO {
    private Connection connection;

    public BookDAOimp(Connection connection) {
        this.connection = connection;
    }
    
    
    @Override
    public void addBook(Book book) {
        String sql = "INSERT INTO Books(bookId, title, author, genre, publicationDate, "
                + "price, quantityInStock, description, coverImageURL) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getBookId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getGenre());
            statement.setDate(5, Date.valueOf(book.getPublicationDate()));
            statement.setBigDecimal(6, book.getPrice());
            statement.setInt(7, book.getQuantityInStock());
            statement.setString(8, book.getDescription());
            statement.setString(9, book.getCoverImageURL());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    
    
}
