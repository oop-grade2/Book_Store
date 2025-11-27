package com.mycompany.bookstore.model;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Orders {
    private int orderId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String paymentStatus;
    
    private Customer customer; // many to one
    private Shipping shipping; // one to one
    private Payments payment; // one to one
    private List<OrderItems> orderItems = new ArrayList<>(); // one to many

    
    public Orders() {
        this.orderItems = new ArrayList<>();
    }

    // here order id is auto generated 
    public Orders(LocalDate orderDate, BigDecimal totalAmount, String paymentStatus, 
                  Customer customer, Shipping shipping, Payments payment) {
        this();
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.customer = customer;
        this.shipping = shipping;
        this.payment = payment;
    }

    // to retrieve data from database
    public Orders(int orderId, LocalDate orderDate, BigDecimal totalAmount, String paymentStatus, 
                  Customer customer, Shipping shipping, Payments payment) {
        this(orderDate, totalAmount, paymentStatus, customer, shipping, payment);
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Payments getPayment() {
        return payment;
    }

    public void setPayment(Payments payment) {
        this.payment = payment;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }
     
    
    public void addOrderItem(OrderItems item) {
        this.orderItems.add(item);
    }

    public void removeOrderItem(OrderItems item) {
        this.orderItems.remove(item);
    }

}
