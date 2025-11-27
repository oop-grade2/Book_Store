package com.mycompany.bookstore.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Shipping {
    private int shippingId;
    private String shippingAddress;
    private String deliveryStatus;
    private String trackingNumber;
    private BigDecimal shippingCost;
    private LocalDate expectedDeliveryDate;
    
    private Orders order; // one to one

    public Shipping() {
    }
    
    public Shipping(String shippingAddress, String deliveryStatus, 
            String trackingNumber, BigDecimal shippingCost, 
            LocalDate expectedDeliveryDate, Orders order) {
        this.shippingAddress = shippingAddress;
        this.deliveryStatus = deliveryStatus;
        this.trackingNumber = trackingNumber;
        this.shippingCost = shippingCost;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.order = order;
    }
    
    public Shipping(int shippingId, String shippingAddress, String deliveryStatus, 
        String trackingNumber, BigDecimal shippingCost, 
        LocalDate expectedDeliveryDate, Orders order) {
    this(shippingAddress, deliveryStatus, trackingNumber, shippingCost, expectedDeliveryDate, order);
    this.shippingId = shippingId;
    }

    public int getShippingId() {
        return shippingId;
    }

    public void setShippingId(int shippingId) {
        this.shippingId = shippingId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
    
    public boolean isDelivered() {
        return "DELIVERED".equalsIgnoreCase(this.deliveryStatus);
    }
}
