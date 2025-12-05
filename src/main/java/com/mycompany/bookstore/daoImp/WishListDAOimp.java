package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.WishListDAO;
import com.mycompany.bookstore.model.WishList;
import com.mycompany.bookstore.model.Book;
import com.mycompany.bookstore.model.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WishListDAOimp implements WishListDAO {

    private final Connection connection;

    public WishListDAOimp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addWishListItem(WishList item) {
        String sql = "INSERT INTO WishList (UserID, BookID, DateAdded) VALUES (?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, item.getCustomer().getUserId());
            statement.setString(2, item.getBook().getBookId());
            statement.setDate(3, Date.valueOf(item.getDateAdded()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWishListItem(int userId, String bookId) {
        String sql = "DELETE FROM WishList WHERE UserID = ? AND BookID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setString(2, bookId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<WishList> getWishListByUserId(int userId) {
        List<WishList> wishList = new ArrayList<>();
        String sql = "SELECT w.DateAdded, b.BookID, b.Title, c.UserID, c.FirstName, c.LastName "
                + "FROM WishList w "
                + "JOIN Book b "
                + "ON w.BookID = b.BookID "
                + "JOIN Users c "
                + "ON w.UserID = c.UserID "
                + "WHERE w.UserID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet res = statement.executeQuery()) {
                while (res.next()) {
                    wishList.add(convertToWishList(res));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    private WishList convertToWishList(ResultSet res) throws SQLException {
        Book book = new Book();
        book.setBookId(res.getString("BookID"));
        book.setTitle(res.getString("Title"));

        Customer customer = new Customer();
        customer.setUserId(res.getInt("UserID"));
        customer.setFirstName(res.getString("FirstName"));
        customer.setLastName(res.getString("LastName"));

        WishList item = new WishList();
        item.setDateAdded(res.getObject("DateAdded", LocalDate.class));
        item.setBook(book);
        item.setCustomer(customer);

        return item;
    }
}