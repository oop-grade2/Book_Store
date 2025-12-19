package com.mycompany.bookstore.service;

import com.mycompany.bookstore.daoImp.PaymentsDAOimp;
import com.mycompany.bookstore.model.Payments;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class PaymentsService {

    private PaymentsDAOimp paymentsDAO;

    public PaymentsService(Connection connection) {
        this.paymentsDAO = new PaymentsDAOimp(connection);
    }


    public void addPayment(Payments payment) {

        if (payment == null)
            throw new IllegalArgumentException("Payment cannot be null");

        if (payment.getOrder() == null || payment.getOrder().getOrderId() <= 0)
            throw new IllegalArgumentException("Payment must be linked to a valid order");

        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Payment amount must be greater than zero");

        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().isBlank())
            throw new IllegalArgumentException("Payment method is required");

        if (payment.getTransactionDate() == null) {
            payment.setTransactionDate(LocalDate.now());
        }

        paymentsDAO.addPayment(payment);
    }

    public void updatePayment(Payments payment) {

        if (payment == null)
            throw new IllegalArgumentException("Payment cannot be null");

        paymentsDAO.updatePayment(payment);
    }


    public void deletePayment(int paymentId) {
        if (paymentId <= 0)
            throw new IllegalArgumentException("Invalid payment id");

        paymentsDAO.deletePayment(paymentId);
    }


    public Payments getPaymentById(int paymentId) {
        if (paymentId <= 0)
            throw new IllegalArgumentException("Invalid payment id");

        return paymentsDAO.getPaymentById(paymentId);
    }


    public List<Payments> getAllPayments() {
        return paymentsDAO.getAllPayments();
    }


    public boolean isOrderPaid(int paymentId) {
        Payments payment = getPaymentById(paymentId);
        return payment != null && payment.isPaid();
    }
}
