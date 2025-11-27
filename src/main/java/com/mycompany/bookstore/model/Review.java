package com.mycompany.bookstore.model;
import java.time.LocalDate;
import java.math.BigDecimal;

public class Review {
    private int reviewId;
    private String reviewText;
    private BigDecimal rating;
    private LocalDate reviewDate;
    
    private Book book;         // many to one
    private Customer customer; // mant to one

    public Review() {
    }
    
    public Review(String reviewText, BigDecimal rating, LocalDate reviewDate, 
            Book book, Customer customer) {
        this.reviewText = reviewText;
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.book = book;
        this.customer = customer;
    }
    
    public Review(int reviewId, String reviewText, BigDecimal rating, 
            LocalDate reviewDate, Book book, Customer customer) {
    this(reviewText, rating, reviewDate, book, customer);
    this.reviewId = reviewId;
}

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    
    public boolean isPositive() {
        return (rating != null && rating.compareTo(BigDecimal.valueOf(3)) > 0);
    }

}
