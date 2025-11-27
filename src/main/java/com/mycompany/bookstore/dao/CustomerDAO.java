package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.Customer;
import java.util.List;

public interface CustomerDAO {
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int userId);
    Customer getCustomerById(int userId);
    List<Customer> getAllCustomers();
}
