package com.mycompany.bookstore.main;
//import com.mycompany.bookstore.gui.LoginFrame;
import com.mycompany.bookstore.util.DBConnection;

import com.mycompany.bookstore.dao.BookDAO;
import com.mycompany.bookstore.daoImp.BookDAOimp;
import com.mycompany.bookstore.model.Book;

import java.sql.Connection;
import java.time.LocalDate;
import java.math.BigDecimal;

public class BookStore {
    public static void main(String[] args) {
    //Connection connection = DBConnection.getConnection();
    
            Connection connection = null;
        try {
                connection = DBConnection.getConnection(); 
            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to connect to database.");
                return;
            }

            
            BookDAO bookDAO = new BookDAOimp(connection);


            Book book = new Book();
            book.setBookId("Null");
            book.setTitle("My Test Book");
            book.setAuthor("Reham Hesham");
            book.setGenre("Programming");
            book.setPublicationDate(LocalDate.of(2025, 12, 19));
            book.setPrice(BigDecimal.valueOf(99.99));
            book.setQuantityInStock(50);
            book.setDescription("This is a test book");
            book.setCoverImageURL(null); 


            bookDAO.addBook(book);
            System.out.println("Book added: " + book.getTitle());


            Book retrieved = bookDAO.getBookById("MY12025");
            if (retrieved != null) {
                System.out.println("Retrieved Book: " + retrieved.getTitle() + " by " + retrieved.getAuthor());
            } else {
                System.out.println("Book not found in DB!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
    
    
    
    
    
    /*java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    */
    
  }
}