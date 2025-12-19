package com.mycompany.bookstore.service;

import com.mycompany.bookstore.dao.BookDAO;
import com.mycompany.bookstore.model.Book;

import java.math.BigDecimal;
import java.util.List;

public class BookService {

    private BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public void addBook(Book book) {

        validateBook(book);

        bookDAO.addBook(book);
    }

    public void updateBook(Book book) {

        if (book.getBookId() == null || book.getBookId().isEmpty()) {
            throw new IllegalArgumentException("Book ID is required for update");
        }

        validateBook(book);

        bookDAO.updateBook(book);
    }


    public void deleteBook(String bookId) {

        if (bookId == null || bookId.isEmpty()) {
            throw new IllegalArgumentException("Book ID is required");
        }

        bookDAO.deleteBook(bookId);
    }


    public Book getBookById(String bookId) {

        if (bookId == null || bookId.isEmpty()) {
            throw new IllegalArgumentException("Book ID is required");
        }

        return bookDAO.getBookById(bookId);
    }


    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }


    private void validateBook(Book book) {

        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }

        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Author is required");
        }

        if (book.getPublicationDate() == null) {
            throw new IllegalArgumentException("Publication date is required");
        }

        if (book.getPrice() == null || book.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be >= 0");
        }

        if (book.getQuantityInStock() == null || book.getQuantityInStock() < 0) {
            throw new IllegalArgumentException("Quantity must be >= 0");
        }
    }
}