package com.mycompany.bookstore.service;

import com.mycompany.bookstore.dao.OrderItemsDAO;
import com.mycompany.bookstore.daoImp.OrderItemsDAOimp;
import com.mycompany.bookstore.model.OrderItems;
import java.sql.Connection;
import java.util.List;

public class OrderItemsService {
    private OrderItemsDAO orderItemsDAO;

    public OrderItemsService(Connection connection) {
        this.orderItemsDAO = new OrderItemsDAOimp(connection);
    }

    public void addOrderItem(OrderItems item) {
        orderItemsDAO.addOrderItem(item);
    }

    public void updateOrderItem(OrderItems item) {
        orderItemsDAO.updateOrderItem(item);
    }

    public void deleteOrderItem(int orderId, String bookId) {
        orderItemsDAO.deleteOrderItem(orderId, bookId);
    }

    public List<OrderItems> getOrderItemsByOrderId(int orderId) {
        return orderItemsDAO.getOrderItemsByOrderId(orderId);
    }

    public List<OrderItems> getAllOrderItems() {
        return orderItemsDAO.getAllOrderItems();
    }
}