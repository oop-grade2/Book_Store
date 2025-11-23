package com.mycompany.bookstore.daoImp;
import com.mycompany.bookstore.dao.BookDAO;
import com.mycompany.bookstore.model.Book;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class BookDAOimp implements BookDAO {
    private Connection connection;

    public BookDAOimp(Connection connection) {
        this.connection = connection;
    }
    
    
    private String generateBookId(Book book) throws SQLException {
        String firstTwo = book.getTitle().length() >= 2 ?
                book.getTitle().substring(0, 2).toUpperCase() :
                book.getTitle().toUpperCase();

        int year = book.getPublicationDate().getYear();

        String sql = "SELECT COUNT(*) FROM Book";
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(sql);
        int serial = 1;
        
        if (res.next()) {
            serial = res.getInt(1) + 1;
        }
        return firstTwo + serial + year;
    }

    
     @Override
    public void addBook(Book book) {
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
                statement.setInt(10, book.getSubcategory().getSubCategoryId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error! Please try again");
            e.printStackTrace();
        }
}
    
    
    @Override
    public void updateBook(Book book) {
        String sql = "UPDATE Book SET Title=?, Author=?, Genre=?, "
                + "PublicationDate=?, Price=?, QuantityInStock=?, [Description]=?, "
                + "CoverImageURL=?, SubCategoryID=? WHERE BookID=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setDate(4, Date.valueOf(book.getPublicationDate()));
            statement.setBigDecimal(5, book.getPrice());
            statement.setInt(6, book.getQuantityInStock());
            statement.setString(7, book.getDescription());
            statement.setString(8, book.getCoverImageURL());
            statement.setInt(9, book.getSubcategory().getSubCategoryId());
            statement.setString(10, book.getBookId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error! Please try again");
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
            System.out.println("Error! Please try again");
            e.printStackTrace();
    }
} 

    @Override
    public Book getBookById(String bookId) {
        String sql = "SELECT * FROM Book WHERE bookId=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookId);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return convertToBook(res);
            }
        } catch (SQLException e) {
            System.out.println("Error! Please try again");
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Book> getBooksByTitle(String title) {
    List<Book> books = new ArrayList<>();
    String sql = "SELECT * FROM Book WHERE Title LIKE ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, "%" + title + "%");
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            books.add(convertToBook(res)); 
        }
    } catch (SQLException e) {
        System.out.println("Error! Please try again");
        e.printStackTrace();
    }
    return books;
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
            System.out.println("Error! Please try again");
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
    book.setPublicationDate(res.getDate("PublicationDate").toLocalDate());
    book.setPrice(res.getBigDecimal("Price"));
    book.setQuantityInStock(res.getInt("QuantityInStock"));
    book.setDescription(res.getString("Description"));
    book.setCoverImageURL(res.getString("CoverImageURL"));

    //int subCategoryId =res.getInt("SubCategoryID");
    // book.setSubcategory(subCategoryDAO.getSubCategoryById(subCategoryId));
    return book;
    }
}
