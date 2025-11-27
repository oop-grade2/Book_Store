package com.mycompany.bookstore.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Book {
    private String bookId;
    private String title;
    private String author ;
    private String genre;
    private LocalDate publicationDate;
    private BigDecimal price;
    private Integer quantityInStock;
    private String description;
    private String coverImageURL;
    
    private SubCategory subcategory;     // many to one
    
    // private List<OrderItems> orderItems; // one to many
    // private List<WishList> wishLists;    // one to many
    // private List<Review> reviews;        // one to many

    // if the book id was generated in dao 
    public Book(String title, String author, String genre, 
            LocalDate publicationDate, BigDecimal price, Integer quantityInStock, 
            String description, String coverImageURL, 
            SubCategory subcategory) {
        this.bookId = null;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.description = description;
        this.coverImageURL = coverImageURL;
        this.subcategory = subcategory;
        
    }

    // if we retrived data from the database
    public Book(String bookId, String title, String author, String genre, LocalDate publicationDate, BigDecimal price, Integer quantityInStock, String description, String coverImageURL, SubCategory subcategory) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.description = description;
        this.coverImageURL = coverImageURL;
        this.subcategory = subcategory;
    }
    
    public Book() {}
    
    public SubCategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(SubCategory subcategory) {
        this.subcategory = subcategory;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
     
    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageURL() {
        return coverImageURL;
    }

    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL;
    }

    
}
