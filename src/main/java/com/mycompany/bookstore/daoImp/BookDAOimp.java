package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.BookDAO;
import com.mycompany.bookstore.dao.SubCategoryDAO; 
import com.mycompany.bookstore.model.Book;
import com.mycompany.bookstore.model.SubCategory;
import com.mycompany.bookstore.util.DBConnection;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class BookDAOimp implements BookDAO {
    private Connection connection;
    private SubCategoryDAO subCategoryDAO;

    public BookDAOimp(Connection connection, SubCategoryDAO subCategoryDAO) {
        this.connection = connection;
        this.subCategoryDAO = subCategoryDAO;
    }
    
        public BookDAOimp(Connection connection) {
        this.connection = connection;
    }
    private String generateBookId(Book book) throws SQLException {
        String firstTwo = book.getTitle().length() >= 2 ?
                book.getTitle().substring(0, 2).toUpperCase() :
                book.getTitle().toUpperCase();

        int year = book.getPublicationDate().getYear();

        String sql = "SELECT COUNT(*) FROM Book";
        try(Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(sql)){
        int serial = 1;

        if (res.next()) {
            serial = res.getInt(1) + 1;
        }
        return firstTwo + serial + year;
        }
    }

    
     @Override
    public void addBook(Book book) {
        
        if (book.getPrice() == null || book.getPrice().compareTo(BigDecimal.ZERO) < 0
                || book.getQuantityInStock() < 0) {
            throw new IllegalArgumentException("Price and Quantity must be >= 0");
        }
        
        try {
            book.setBookId(generateBookId(book));

            String sql = "INSERT INTO Book (BookID, Title, Author, Genre, PublicationDate, "
                    + "Price, QuantityInStock, [Description], CoverImageURL, SubCategoryID) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                
                if (book.getSubcategory() != null) {
                    statement.setInt(10, book.getSubcategory().getSubCategoryId());
                } else {
                    statement.setNull(10, Types.INTEGER);
                }
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error! Cannot add book.");
            e.printStackTrace();
        }
    }

    
    @Override
    public void updateBook(Book book) {
        if (book.getPrice() == null || book.getPrice().compareTo(BigDecimal.ZERO) < 0
                || book.getQuantityInStock() < 0) {
            throw new IllegalArgumentException("Price and Quantity must be >= 0");
        }
        
        String sql = "UPDATE Book SET Title=?, Author=?, Genre=?, PublicationDate=?, Price=?, "
                + "QuantityInStock=?, [Description]=?, CoverImageURL=?, SubCategoryID=? WHERE BookID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setDate(4, Date.valueOf(book.getPublicationDate()));
            statement.setBigDecimal(5, book.getPrice());
            statement.setInt(6, book.getQuantityInStock());
            statement.setString(7, book.getDescription());
            statement.setString(8, book.getCoverImageURL());
            
            if (book.getSubcategory() != null) {
                statement.setInt(9, book.getSubcategory().getSubCategoryId());
            } else {
                statement.setNull(9, Types.INTEGER);
            }
            statement.setString(10, book.getBookId());
            statement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error! Cannot update book.");
            e.printStackTrace();
        }
    }

    
    @Override
    public void deleteBook(String bookId) {
        String sql = "DELETE FROM Book WHERE BookID=?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookId);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error! Cannot delete book.");
            e.printStackTrace();
        }
} 

    @Override
    public Book getBookById(String bookId) {
        String sql = "SELECT * FROM Book WHERE BookID=?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookId);
            ResultSet res = statement.executeQuery();
            
            if (res.next()) {
                return convertToBook(res);
            }
            
        } catch (SQLException e) {
            System.out.println("Error! Cannot get book.");
            e.printStackTrace();
        }
        
        return null;
    }
   

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        
        String sql = "SELECT * FROM Book";
        
        try (Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery(sql);
            
            while (res.next()) {
                books.add(convertToBook(res));
            }
            
        } catch (SQLException e) {
            System.out.println("Error! Cannot get books.");
            e.printStackTrace();
        }
        
        return books;
    }
    
    private Book convertToBook(ResultSet res) throws SQLException {
        Book book = new Book();
        book.setBookId(res.getString("BookID"));
        book.setTitle(res.getString("Title"));
        book.setAuthor(res.getString("Author"));
        book.setGenre(res.getString("Genre"));
        
        Date publicationDate = res.getDate("PublicationDate");
        if (publicationDate != null) {
            book.setPublicationDate(publicationDate.toLocalDate());
        } else {
            book.setPublicationDate(null); 
        }
        
        book.setPrice(res.getBigDecimal("Price"));
        book.setQuantityInStock(res.getInt("QuantityInStock"));
        book.setDescription(res.getString("Description"));
        book.setCoverImageURL(res.getString("CoverImageURL"));

        int subCategoryId = res.getInt("SubCategoryID");
        if (!res.wasNull()) {
            SubCategory sub = subCategoryDAO.getSubCategoryById(subCategoryId);
            book.setSubcategory(sub); 
        }
        return book;
    }
    
    
    public void updateQuantity(String bookId, int newQuantity) {

    String sql = "UPDATE Book SET QuantityInStock = ? WHERE BookID = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, newQuantity);
        ps.setString(2, bookId);
        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
    
  public BigDecimal getBookPriceById(String bookId) {

    String sql = "SELECT Price FROM Book WHERE BookID = ?";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setString(1, bookId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getBigDecimal("Price");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return BigDecimal.ZERO; // لو مش لاقي الكتاب
}

    public String getBookTitleById(String bookId) {

    String sql = "SELECT Title FROM Book WHERE BookID = ?";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setString(1, bookId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getString("Title");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null; // لو الكتاب مش موجود
}

    
    
}

