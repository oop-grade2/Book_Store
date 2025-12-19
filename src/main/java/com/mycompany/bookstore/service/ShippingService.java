package com.mycompany.bookstore.service;

import com.mycompany.bookstore.daoImp.ShippingDAOimp;
import com.mycompany.bookstore.model.Shipping;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class ShippingService {

    private ShippingDAOimp shippingDAO;

    public ShippingService(Connection connection) {
        this.shippingDAO = new ShippingDAOimp(connection);
    }

    public void addShipping(Shipping shipping) {

        if (shipping == null)
            throw new IllegalArgumentException("Shipping cannot be null");

        if (shipping.getOrder() == null || shipping.getOrder().getOrderId() <= 0)
            throw new IllegalArgumentException("Order is required");

        if (shipping.getShippingAddress() == null || shipping.getShippingAddress().isBlank())
            throw new IllegalArgumentException("Shipping address is required");

        if (shipping.getDeliveryStatus() == null)
            shipping.setDeliveryStatus("PENDING");

        if (shipping.getExpectedDeliveryDate() == null)
            shipping.setExpectedDeliveryDate(LocalDate.now().plusDays(3));

        shippingDAO.addShipping(shipping);
    }

    public void updateShipping(Shipping shipping) {
        shippingDAO.updateShipping(shipping);
    }

    public void deleteShipping(int id) {
        shippingDAO.deleteShipping(id);
    }

    public Shipping getShippingById(int id) {
        return shippingDAO.getShippingById(id);
    }

    public List<Shipping> getAllShippings() {
        return shippingDAO.getAllShippings();
    }
}

