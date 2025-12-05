package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.ShippingDAO;
import com.mycompany.bookstore.model.Shipping;
import com.mycompany.bookstore.model.Orders;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShippingDAOimp implements ShippingDAO {

    private final Connection connection;

    public ShippingDAOimp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addShipping(Shipping shipping) {
        if (shipping == null) 
            throw new IllegalArgumentException("Shipping cannot be null");
        
        if (shipping.getOrder() == null || shipping.getOrder().getOrderId() <= 0)
            throw new IllegalArgumentException("Shipping must be linked to a valid Order with id");
        
        String sql = " INSERT INTO Shipping (ShippingAddress, DeliveryStatus, TrackingNumber," 
         +" ShippingCost, ExpectedDeliveryDate, OrderID) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, shipping.getShippingAddress());
            statement.setString(2, shipping.getDeliveryStatus());
            statement.setString(3, shipping.getTrackingNumber());
            
            if (shipping.getShippingCost() != null) {
                statement.setBigDecimal(4, shipping.getShippingCost());
            } else {
                statement.setNull(4, Types.DECIMAL);
            }

            if (shipping.getExpectedDeliveryDate() != null) {
                statement.setDate(5, Date.valueOf(shipping.getExpectedDeliveryDate()));
            } else {
                statement.setNull(5, Types.DATE);
            }
            
            statement.setInt(6, shipping.getOrder().getOrderId());

            statement.executeUpdate();

            
            try (ResultSet Keys = statement.getGeneratedKeys()) {
                if (Keys.next()) {
                    shipping.setShippingId(Keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in addShipping()");
            e.printStackTrace();
        }
    }

    
    @Override
    public void updateShipping(Shipping shipping) {

        if (shipping == null) 
            throw new IllegalArgumentException("Shipping cannot be null");
        
        if (shipping.getShippingId() <= 0) 
            throw new IllegalArgumentException("Shipping must have a valid id");
        
        if (shipping.getOrder() == null || shipping.getOrder().getOrderId() <= 0)
            throw new IllegalArgumentException("Shipping must be linked to a valid Order with id");
        
        String sql = " UPDATE Shipping SET ShippingAddress = ?, DeliveryStatus = ?, TrackingNumber = ?, "
                + "ShippingCost = ?, ExpectedDeliveryDate = ?, OrderID = ? WHERE ShippingID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, shipping.getShippingAddress());
            statement.setString(2, shipping.getDeliveryStatus());
            statement.setString(3, shipping.getTrackingNumber());
            
            if (shipping.getShippingCost() != null) {
                statement.setBigDecimal(4, shipping.getShippingCost());
            } else {
                statement.setNull(4, Types.DECIMAL);
            }

            if (shipping.getExpectedDeliveryDate() != null) {
                statement.setDate(5, Date.valueOf(shipping.getExpectedDeliveryDate()));
            } else {
                statement.setNull(5, Types.DATE);
            }
            
            statement.setInt(6, shipping.getOrder().getOrderId());
            statement.setInt(7, shipping.getShippingId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in updateShipping()");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteShipping(int shippingId) {
        
        if (shippingId <= 0) 
            throw new IllegalArgumentException("shippingId must be > 0");
        
        String sql = "DELETE FROM Shipping WHERE ShippingID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, shippingId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in deleteShipping()");
            e.printStackTrace();
        }
    }

    @Override
    public Shipping getShippingById(int shippingId) {
        String sql = "SELECT s.*, o.OrderId, o.OrderDate, o.TotalAmount, o.PaymentStatus "
                + "FROM Shipping s "
                + "JOIN Orders o "
                + "ON s.OrderID = o.OrderId "
                + "WHERE s.ShippingID = ? ";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, shippingId);
            
            try (ResultSet res = statement.executeQuery()) {
            if (res.next()) {
                Orders order = new Orders();
                int orderId = res.getInt("o_OrderID");
                    if (!res.wasNull()) {
                        order.setOrderId(orderId);

                Date orderDate = res.getDate("OrderDate");
                
                if (orderDate != null) order.setOrderDate(orderDate.toLocalDate());

                        order.setTotalAmount(res.getBigDecimal("TotalAmount"));
                        order.setPaymentStatus(res.getString("PaymentStatus"));
                    } else {
                        order = null; 
                    }

                Date expected = res.getDate("ExpectedDeliveryDate");
                Shipping shipping = new Shipping();
                shipping.setShippingId(res.getInt("ShippingID"));
                shipping.setShippingAddress(res.getString("ShippingAddress"));
                shipping.setDeliveryStatus(res.getString("DeliveryStatus"));
                shipping.setTrackingNumber(res.getString("TrackingNumber"));
                shipping.setShippingCost(res.getBigDecimal("ShippingCost"));
                
                if (expected != null) 
                    shipping.setExpectedDeliveryDate(expected.toLocalDate());
                
                shipping.setOrder(order);
                return shipping;
            }
        }
        
    } catch (SQLException e) {
        System.out.println("Error in getShippingById()");
        e.printStackTrace();
    }
    return null;
}
    
    @Override
    public List<Shipping> getAllShippings() {
        List<Shipping> list = new ArrayList<>();
        String sql = "SELECT s.*, o.OrderID AS o_OrderID, o.OrderDate, o.TotalAmount, o.PaymentStatus "
                   + "FROM Shipping s "
                + "LEFT JOIN Orders o "
                + "ON s.OrderID = o.OrderID";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet res = statement.executeQuery()) {

            while (res.next()) {
                Orders order = new Orders();
                int ordId = res.getInt("o_OrderID");
                if (!res.wasNull()) {
                    order.setOrderId(ordId);
                    
                    Date orderDate = res.getDate("OrderDate");
                    if (orderDate != null) order.setOrderDate(orderDate.toLocalDate());
                    
                    order.setTotalAmount(res.getBigDecimal("TotalAmount"));
                    order.setPaymentStatus(res.getString("PaymentStatus"));
                } else {
                    order = null;
                }

                Shipping shipping = new Shipping();
                shipping.setShippingId(res.getInt("ShippingID"));
                shipping.setShippingAddress(res.getString("ShippingAddress"));
                shipping.setDeliveryStatus(res.getString("DeliveryStatus"));
                shipping.setTrackingNumber(res.getString("TrackingNumber"));
                shipping.setShippingCost(res.getBigDecimal("ShippingCost"));

                Date expected = res.getDate("ExpectedDeliveryDate");
                if (expected != null) shipping.setExpectedDeliveryDate(expected.toLocalDate());

                shipping.setOrder(order);
                list.add(shipping);
            }

        } catch (SQLException e) {
            System.out.println("Error in getAllShippings(): " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }
}