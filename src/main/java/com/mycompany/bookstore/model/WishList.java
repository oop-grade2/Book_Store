package com.mycompany.bookstore.model;
import java.time.LocalDate;

public class WishList {
    private LocalDate dateAdded;

    private Customer customer; // many to one
    private Book book;         // many to one

    public WishList() {
    }
    
    public WishList(LocalDate dateAdded, Customer customer, Book book) {
        this.dateAdded = dateAdded;
        this.customer = customer;
        this.book = book;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
}
