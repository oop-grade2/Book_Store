package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.OrderItemsDAO;
import com.mycompany.bookstore.model.Book;
import com.mycompany.bookstore.model.OrderItems;
import com.mycompany.bookstore.model.Orders;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class OrderItemsDAOimp implements OrderItemsDAO {

    private Connection connection;

    public OrderItemsDAOimp (Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addOrderItem(OrderItems item) {
        if (item == null) 
            throw new IllegalArgumentException("OrderItems cannot be null");
        
        if (item.getOrder() == null || item.getOrder().getOrderId() <= 0)
            throw new IllegalArgumentException("Order must have a valid id");
        
        if (item.getBook() == null || item.getBook().getBookId() == null)
            throw new IllegalArgumentException("Book must have a valid id");
        
        if (item.getQuantity() <= 0) 
            throw new IllegalArgumentException("Quantity must be >= 1");
        
        if (item.getTotalPrice() == null || item.getTotalPrice().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("TotalPrice must be >= 0");
        

        String sql = "INSERT INTO OrderItems (OrderID, BookID, Quantity, TotalPrice) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, item.getOrder().getOrderId());
            statement.setString(2, item.getBook().getBookId());
            statement.setInt(3, item.getQuantity());
            statement.setBigDecimal(4, item.getTotalPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in addOrderItem()");
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrderItem(OrderItems item) {

        if (item == null) 
            throw new IllegalArgumentException("OrderItems cannot be null");
        
        if (item.getOrder() == null || item.getOrder().getOrderId() <= 0)
            throw new IllegalArgumentException("Order must have a valid id");
        
        if (item.getBook() == null || item.getBook().getBookId() == null)
            throw new IllegalArgumentException("Book must have a valid id");
        
        if (item.getQuantity() <= 0) 
            throw new IllegalArgumentException("Quantity must be >= 1");
        
        if (item.getTotalPrice() == null || item.getTotalPrice().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("TotalPrice must be >= 0");
        
        String sql = "UPDATE OrderItems SET Quantity=?, TotalPrice=? WHERE OrderID=? AND BookID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, item.getQuantity());
            statement.setBigDecimal(2, item.getTotalPrice());
            statement.setInt(3, item.getOrder().getOrderId());
            statement.setString(4, item.getBook().getBookId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in updateOrderItem()");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderItem(int orderId, String bookId) {
        
        if (orderId <= 0) 
            throw new IllegalArgumentException("orderId must be > 0");
        
        if (bookId == null || bookId.isBlank()) 
            throw new IllegalArgumentException("bookId must be provided");

        String sql = "DELETE FROM OrderItems WHERE OrderID=? AND BookID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);
            statement.setString(2, bookId);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in deleteOrderItem()");
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderItems> getOrderItemsByOrderId(int orderId) {

        List<OrderItems> list = new ArrayList<>();
        String sql = "SELECT * FROM OrderItems WHERE OrderID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);
            try(ResultSet res = statement.executeQuery()){

            while (res.next()) {

                Orders order = new Orders();
                order.setOrderId(orderId);

                Book book = new Book();
                book.setBookId(res.getString("BookID"));

                OrderItems item = new OrderItems(
                        res.getInt("Quantity"),
                        res.getBigDecimal("TotalPrice"),
                        order,
                        book
                );

                list.add(item);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in getOrderItemsByOrderId()");
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<OrderItems> getAllOrderItems() {

        List<OrderItems> list = new ArrayList<>();
        String sql = "SELECT * FROM OrderItems";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet res = statement.executeQuery();

            while (res.next()) {

                Orders order = new Orders();
                order.setOrderId(res.getInt("OrderID"));

                Book book = new Book();
                book.setBookId(res.getString("BookID"));

                OrderItems item = new OrderItems(
                        res.getInt("Quantity"),
                        res.getBigDecimal("TotalPrice"),
                        order,
                        book
                );

                list.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error in getAllOrderItems()");
            e.printStackTrace();
        }

        return list;
    }
}
