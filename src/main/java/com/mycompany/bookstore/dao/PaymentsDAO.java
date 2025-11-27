package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.Payments;
import java.util.List;

public interface PaymentsDAO {
    void addPayment(Payments payment);
    void updatePayment(Payments payment);
    void deletePayment(int paymentId);
    Payments getPaymentById(int paymentId);
    List<Payments> getAllPayments();
}
