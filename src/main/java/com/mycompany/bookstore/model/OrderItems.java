package com.mycompany.bookstore.model;
import java.math.BigDecimal;

public class OrderItems {
    private int quantity;
    private BigDecimal totalPrice;
    
    private Orders order; // many to one
    private Book book; // many to one

    public OrderItems() {
    }
    
    public OrderItems(int quantity, Orders order, Book book) {
        this.quantity = quantity;
        this.order = order;
        this.book = book;
        this.totalPrice = calculateTotalPrice();
    }

    // to retrieve data from database
    public OrderItems(int quantity, BigDecimal totalPrice, Orders order, Book book) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.order = order;
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice();
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
        this.totalPrice = calculateTotalPrice();
    }
    
    public BigDecimal calculateTotalPrice() {
        
        if(book == null || book.getPrice() == null) 
            return BigDecimal.ZERO;
        
        return book.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
    
}
