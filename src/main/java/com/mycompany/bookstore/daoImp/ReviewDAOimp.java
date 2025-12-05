package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.ReviewDAO;
import com.mycompany.bookstore.model.Review;
import com.mycompany.bookstore.model.Book;
import com.mycompany.bookstore.model.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOimp implements ReviewDAO {

    private final Connection connection;

    public ReviewDAOimp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addReview(Review review) {
        if (review.getBook() == null || review.getBook().getBookId() == null)
            throw new IllegalArgumentException("Book must not be null");
        if (review.getCustomer() == null || review.getCustomer().getUserId() <= 0)
            throw new IllegalArgumentException("Customer must not be null");

        String sql = "INSERT INTO Review (ReviewText, Rating, ReviewDate, "
                + "BookID, CustomerID) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, review.getReviewText());
            statement.setBigDecimal(2, review.getRating());
            statement.setDate(3, Date.valueOf(review.getReviewDate()));
            statement.setString(4, review.getBook().getBookId());
            statement.setInt(5, review.getCustomer().getUserId());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) 
                    review.setReviewId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateReview(Review review) {
        String sql = "UPDATE Review SET ReviewText = ?, Rating = ?, ReviewDate = ?,"
                + " BookID = ?, CustomerID = ? WHERE ReviewID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, review.getReviewText());
            statement.setBigDecimal(2, review.getRating());
            statement.setDate(3, Date.valueOf(review.getReviewDate()));
            statement.setString(4, review.getBook().getBookId());
            statement.setInt(5, review.getCustomer().getUserId());
            statement.setInt(6, review.getReviewId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReview(int reviewId) {
        String sql = "DELETE FROM Review WHERE ReviewID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reviewId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Review getReviewById(int reviewId) {
        String sql = "SELECT r.*, b.BookId, b.Title, c.CustomerId, c.FirstName, c.LastName " +
                     "FROM Review r " +
                     "JOIN Book b ON r.BookID = b.BookId " +
                     "JOIN Customer c ON r.CustomerID = c.CustomerId " +
                     "WHERE r.ReviewID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reviewId);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) 
                    return convertToReview(res);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return null;
    }

    @Override
    public List<Review> getReviewsByBookId(String bookId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, b.BookId, b.Title, c.CustomerId, c.FirstName, c.LastName " +
                     "FROM Review r " +
                     "JOIN Book b ON r.BookID = b.BookId " +
                     "JOIN Customer c ON r.CustomerID = c.CustomerId " +
                     "WHERE r.BookID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookId);
            try (ResultSet res = statement.executeQuery()) {
                while (res.next()) 
                    reviews.add(convertToReview(res));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return reviews;
    }

    @Override
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, b.BookId, b.Title, c.CustomerId, c.FirstName, c.LastName " +
                     "FROM Review r " +
                     "JOIN Book b ON r.BookID = b.BookId " +
                     "JOIN Customer c ON r.CustomerID = c.CustomerId";
        
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet res = statement.executeQuery()) {
            while (res.next()) reviews.add(convertToReview(res));
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return reviews;
    }

    private Review convertToReview(ResultSet res) throws SQLException {
        Book book = new Book();
        book.setBookId(res.getString("BookId"));
        book.setTitle(res.getString("Title"));

        Customer customer = new Customer();
        customer.setUserId(res.getInt("CustomerId"));
        customer.setFirstName(res.getString("FirstName"));
        customer.setLastName(res.getString("LastName"));

        LocalDate date = res.getDate("ReviewDate").toLocalDate();

        return new Review(
                res.getInt("ReviewID"),
                res.getString("ReviewText"),
                res.getBigDecimal("Rating"),
                date,
                book,
                customer
        );
    }
}