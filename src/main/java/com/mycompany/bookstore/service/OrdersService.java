package com.mycompany.bookstore.service;

import com.mycompany.bookstore.daoImp.OrdersDAOimp;
import com.mycompany.bookstore.daoImp.OrderItemsDAOimp;
import com.mycompany.bookstore.model.OrderItems;
import com.mycompany.bookstore.model.Orders;

import java.sql.Connection;
import java.math.BigDecimal;

public class OrdersService {

    private OrdersDAOimp ordersDAO;
    private OrderItemsDAOimp orderItemsDAO;

    public OrdersService(Connection connection) {
        this.ordersDAO = new OrdersDAOimp(connection);
        this.orderItemsDAO = new OrderItemsDAOimp(connection);
    }


    public void placeOrder(Orders order) {

        if (order == null)
            throw new IllegalArgumentException("Order cannot be null");

        if (order.getCustomer() == null)
            throw new IllegalArgumentException("Order must have a customer");

        if (order.getOrderItems().isEmpty())
            throw new IllegalArgumentException("Order must have at least one item");


        BigDecimal total = BigDecimal.ZERO;
        for (OrderItems item : order.getOrderItems()) {
            total = total.add(item.getTotalPrice());
        }
        order.setTotalAmount(total);


        ordersDAO.addOrder(order);


        for (OrderItems item : order.getOrderItems()) {
            item.setOrder(order); 
            orderItemsDAO.addOrderItem(item);
        }
    }

    public Orders getOrderById(int orderId) {
        return ordersDAO.getOrderById(orderId);
    }
}