package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.Shipping;
import java.util.List;

public interface ShippingDAO {
    void addShipping(Shipping shipping);
    void updateShipping(Shipping shipping);
    void deleteShipping(int shippingId);
    Shipping getShippingById(int shippingId);
    List<Shipping> getAllShippings();
}
