package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.Book;
import java.util.List;


public interface BookDAO {
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(String bookId);
    Book getBookById(String bookId);
    List<Book> getBooksByTitle(String title);
    List<Book> getAllBooks();
}
