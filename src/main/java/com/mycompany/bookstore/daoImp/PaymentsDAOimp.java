package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.PaymentsDAO;
import com.mycompany.bookstore.model.Payments;
import com.mycompany.bookstore.model.Orders;
import com.mycompany.bookstore.model.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentsDAOimp implements PaymentsDAO {

    private final Connection connection;

    public PaymentsDAOimp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addPayment(Payments payment) {
        if (payment == null) 
            throw new IllegalArgumentException("Payment cannot be null");
        
        if (payment.getOrder() == null || payment.getOrder().getOrderId() <= 0)
            throw new IllegalArgumentException("Payment must be linked to a valid Order");
        
        String sql = "INSERT INTO Payments (Amount, PaymentMethod, TransactionDate, OrderID) VALUES (?, ?, ?, ?)";

               try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (payment.getAmount() != null) {
                statement.setBigDecimal(1, payment.getAmount());
            } else {
                statement.setNull(1, Types.DECIMAL);
            }

            statement.setString(2, payment.getPaymentMethod());

            if (payment.getTransactionDate() != null) {
                statement.setDate(3, Date.valueOf(payment.getTransactionDate()));
            } else {
                statement.setNull(3, Types.DATE);
            }

            statement.setInt(4, payment.getOrder().getOrderId());

            statement.executeUpdate();

            try (ResultSet Keys = statement.getGeneratedKeys()) {
                if (Keys.next()) {
                    payment.setPaymentId(Keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in addPayment(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
   public void updatePayment(Payments payment) {
        if (payment == null) 
            throw new IllegalArgumentException("Payment cannot be null");
        
        if (payment.getPaymentId() <= 0) 
            throw new IllegalArgumentException("Payment must have a valid id to update");
        
        if (payment.getOrder() == null || payment.getOrder().getOrderId() <= 0)
            throw new IllegalArgumentException("Payment must be linked to a valid Order");

        String sql = "UPDATE Payments SET Amount = ?, PaymentMethod = ?, TransactionDate = ?, OrderID = ? WHERE PaymentID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            if (payment.getAmount() != null) {
                statement.setBigDecimal(1, payment.getAmount());
            } else {
                statement.setNull(1, Types.DECIMAL);
            }

            statement.setString(2, payment.getPaymentMethod());

            if (payment.getTransactionDate() != null) {
                statement.setDate(3, Date.valueOf(payment.getTransactionDate()));
            } else {
                statement.setNull(3, Types.DATE);
            }

            statement.setInt(4, payment.getOrder().getOrderId());
            statement.setInt(5, payment.getPaymentId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in updatePayment(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deletePayment(int paymentId) {
         if (paymentId <= 0) 
             throw new IllegalArgumentException("paymentId must be > 0");

        String sql = "DELETE FROM Payments WHERE PaymentID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, paymentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in deletePayment(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Payments getPaymentById(int paymentId) {
        String sql = "SELECT p.*, o.OrderID AS o_OrderID, o.OrderDate, o.TotalAmount, o.PaymentStatus, o.UserID " +
                 "FROM Payments p " +
                 "JOIN Orders o "
                + "ON p.OrderID = o.OrderID " +
                 "WHERE p.PaymentID = ?";

    Payments payment = null;

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, paymentId);
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

                    // build Customer and attach to order
                    int userId = res.getInt("UserID");
                    if (!res.wasNull()) {
                        Customer customer = new Customer();
                        customer.setUserId(userId);
                        order.setCustomer(customer);
                    }
                } else {
                    order = null;
                }

                Date transDate = res.getDate("TransactionDate");
                LocalDate transactionDate = transDate != null ? transDate.toLocalDate() : null;

                payment = new Payments();
                payment.setPaymentId(res.getInt("PaymentID"));
                payment.setAmount(res.getBigDecimal("Amount"));
                payment.setPaymentMethod(res.getString("PaymentMethod"));
                payment.setTransactionDate(transactionDate);
                payment.setOrder(order);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error in getPaymentById(): " + e.getMessage());
        e.printStackTrace();
    }

    return payment;
}

    @Override
public List<Payments> getAllPayments() {
    List<Payments> payments = new ArrayList<>();
    String sql = "SELECT p.*, o.OrderID AS o_OrderID, o.OrderDate, o.TotalAmount, o.PaymentStatus, o.UserID " 
            + "FROM Payments p " 
            + "JOIN Orders o "
            + "ON p.OrderID = o.ORDERID";

    try (PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet res = statement.executeQuery()) {

        while (res.next()) {
            Orders order = new Orders();
            int orderId = res.getInt("o_OrderID");
            if (!res.wasNull()) {
                order.setOrderId(orderId);

                Date orderDate = res.getDate("OrderDate");
                if (orderDate != null) order.setOrderDate(orderDate.toLocalDate());

                order.setTotalAmount(res.getBigDecimal("TotalAmount"));
                order.setPaymentStatus(res.getString("PaymentStatus"));

                // build Customer and attach to order
                int userId = res.getInt("UserID");
                if (!res.wasNull()) {
                    Customer customer = new Customer();
                    customer.setUserId(userId);
                    order.setCustomer(customer);
                } else {
                    order.setCustomer(null);
                }
            } else {
                order = null;
            }

            Date transDate = res.getDate("TransactionDate");
            LocalDate transactionDate = transDate != null ? transDate.toLocalDate() : null;

            Payments payment = new Payments();
            payment.setPaymentId(res.getInt("PaymentID"));
            payment.setAmount(res.getBigDecimal("Amount"));
            payment.setPaymentMethod(res.getString("PaymentMethod"));
            payment.setTransactionDate(transactionDate);
            payment.setOrder(order);

            payments.add(payment);
        }

    } catch (SQLException e) {
        System.out.println("Error in getAllPayments(): " + e.getMessage());
        e.printStackTrace();
    }

    return payments;
    }
}