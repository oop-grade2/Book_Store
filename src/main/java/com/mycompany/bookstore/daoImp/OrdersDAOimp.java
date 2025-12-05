package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.OrdersDAO;
import com.mycompany.bookstore.model.Customer;
import com.mycompany.bookstore.model.Orders;
import com.mycompany.bookstore.model.Payments;
import com.mycompany.bookstore.model.Shipping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAOimp implements OrdersDAO {

    private Connection connection;

    public OrdersDAOimp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addOrder(Orders order) {
        if (order == null) 
            throw new IllegalArgumentException("Order cannot be null");
        
        if (order.getCustomer() == null || order.getCustomer().getUserId() <= 0)
            throw new IllegalArgumentException("Order must be linked to a valid Customer (userId)");

        String sql = "INSERT INTO Orders (OrderDate, TotalAmount, PaymentStatus, UserID) VALUES (?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (order.getOrderDate() != null) {
                statement.setDate(1, Date.valueOf(order.getOrderDate()));
            } else {
                statement.setNull(1, Types.DATE);
            }

            if (order.getTotalAmount() != null) {
                statement.setBigDecimal(2, order.getTotalAmount());
            } else {
                statement.setNull(2, Types.DECIMAL);
            }

            statement.setString(3, order.getPaymentStatus());
            statement.setInt(4, order.getCustomer().getUserId());

            statement.executeUpdate();

            try (ResultSet res = statement.getGeneratedKeys()) {
                if (res.next()) {
                    order.setOrderId(res.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in addOrder(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrder(Orders order) {

        if (order == null) 
            throw new IllegalArgumentException("Order cannot be null");
        if (order.getOrderId() <= 0) 
            throw new IllegalArgumentException("Order must have a valid id");
        if (order.getCustomer() == null || order.getCustomer().getUserId() <= 0)
            throw new IllegalArgumentException("Order must be linked to a valid Customer (userId)");
        
        String sql = "UPDATE Orders SET OrderDate=?, TotalAmount=?, PaymentStatus=?, UserID=? WHERE OrderID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            if (order.getOrderDate() != null) {
                statement.setDate(1, Date.valueOf(order.getOrderDate()));
            } else {
                statement.setNull(1, Types.DATE);
            }

            if (order.getTotalAmount() != null) {
                statement.setBigDecimal(2, order.getTotalAmount());
            } else {
                statement.setNull(2, Types.DECIMAL);
            }

            statement.setString(3, order.getPaymentStatus());
            statement.setInt(4, order.getCustomer().getUserId());
            statement.setInt(5, order.getOrderId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in updateOrder(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(int orderId) {

        if (orderId <= 0) 
            throw new IllegalArgumentException("orderId must be > 0");
        
        String sql = "DELETE FROM Orders WHERE OrderID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in deleteOrder(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Orders getOrderById(int orderId) {
        String sql = "SELECT * FROM Orders WHERE OrderID=?";
        Orders order = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    order = new Orders();
                    order.setOrderId(orderId);

                    Date orderDate = res.getDate("OrderDate");
                    if (orderDate != null) order.setOrderDate(orderDate.toLocalDate());

                    order.setTotalAmount(res.getBigDecimal("TotalAmount"));
                    order.setPaymentStatus(res.getString("PaymentStatus"));

                    Customer customer = new Customer();
                    int userId = res.getInt("UserID");
                    if (!res.wasNull()) {
                        customer.setUserId(userId);
                        order.setCustomer(customer);
                    } else {
                        order.setCustomer(null);
                    }

                    
                    Shipping shipping = loadShippingForOrder(orderId);
                    order.setShipping(shipping); 

                    
                    Payments payment = loadPaymentsForOrder(orderId);
                    order.setPayment(payment); 
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in getOrderById(): " + e.getMessage());
            e.printStackTrace();
        }

        return order;
    }
    

    @Override
    public List<Orders> getAllOrders() {
        List<Orders> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet res = statement.executeQuery()) {

            while (res.next()) {
                Orders order = new Orders();
                int orderId = res.getInt("OrderID");
                order.setOrderId(orderId);

                Date orderDate = res.getDate("OrderDate");
                if (orderDate != null) order.setOrderDate(orderDate.toLocalDate());

                order.setTotalAmount(res.getBigDecimal("TotalAmount"));
                order.setPaymentStatus(res.getString("PaymentStatus"));

                Customer customer = new Customer();
                int userId = res.getInt("UserID");
                if (!res.wasNull()) {
                    customer.setUserId(userId);
                    order.setCustomer(customer);
                } else {
                    order.setCustomer(null);
                }

                order.setShipping(loadShippingForOrder(orderId));
                order.setPayment(loadPaymentsForOrder(orderId));

                list.add(order);
            }

        } catch (SQLException e) {
            System.out.println("Error in getAllOrders(): " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }
    
    
    private Shipping loadShippingForOrder(int orderId) {
        String sql = "SELECT * FROM Shipping WHERE OrderID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    Shipping shipping = new Shipping();
                    shipping.setShippingId(res.getInt("ShippingID"));
                    shipping.setShippingAddress(res.getString("ShippingAddress"));
                    shipping.setDeliveryStatus(res.getString("DeliveryStatus"));
                    shipping.setTrackingNumber(res.getString("TrackingNumber"));
                    shipping.setShippingCost(res.getBigDecimal("ShippingCost"));

                    Date expected = res.getDate("ExpectedDeliveryDate");
                    if (expected != null) 
                        shipping.setExpectedDeliveryDate(expected.toLocalDate());

                    return shipping;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in loadShippingForOrder(): " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private Payments loadPaymentsForOrder(int orderId) {
        String sql = "SELECT * FROM Payments WHERE OrderID = ? ORDER BY PaymentDate";

        try (PreparedStatement tatement = connection.prepareStatement(sql)) {
            tatement.setInt(1, orderId);
            
            try (ResultSet res = tatement.executeQuery()) {
                while (res.next()) {
                    Payments payment = new Payments();
                    payment.setPaymentId(res.getInt("PaymentID"));
                    Date payDate = res.getDate("PaymentDate");
                    
                    if (payDate != null) 
                        payment.setTransactionDate(payDate.toLocalDate());
                    
                    payment.setAmount(res.getBigDecimal("Amount"));
                    payment.setPaymentMethod(res.getString("PaymentMethod"));

                    return payment;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in loadPaymentsForOrder(): " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}

