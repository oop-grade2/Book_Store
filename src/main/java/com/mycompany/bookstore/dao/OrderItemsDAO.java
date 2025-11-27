package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.OrderItems;
import java.util.List;

public interface OrderItemsDAO {
    void addOrderItem(OrderItems item);
    void updateOrderItem(OrderItems item);
    void deleteOrderItem(int orderId, int bookId);
    List<OrderItems> getOrderItemsByOrderId(int orderId);
    List<OrderItems> getAllOrderItems();
}
