package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.Orders;
import java.util.List;

public interface OrdersDAO {
    void addOrder(Orders order);
    void updateOrder(Orders order);
    void deleteOrder(int orderId);
    Orders getOrderById(int orderId);
    List<Orders> getAllOrders();
}
