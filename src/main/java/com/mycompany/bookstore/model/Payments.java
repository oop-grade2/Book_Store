package com.mycompany.bookstore.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Payments {
    private Integer paymentId;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDate transactionDate;
   
    private Orders order; // one to one

    
    public Payments() {
    }
    
    public Payments(BigDecimal amount, String paymentMethod, LocalDate transactionDate, Orders order) {
        this.paymentId = null;         
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionDate = transactionDate;
        this.order = order;
    }

        // to retrieve data from database
    public Payments(Integer paymentId, BigDecimal amount, String paymentMethod, LocalDate transactionDate, Orders order) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionDate = transactionDate;
        this.order = order;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
    
    public boolean isPaid() {
        if (paymentMethod == null) return false;

        if (paymentMethod.equalsIgnoreCase("PAID")) 
            return true;
        else if (paymentMethod.equalsIgnoreCase("SUCCESS")) 
            return true;

    return false;
    }
    
}
