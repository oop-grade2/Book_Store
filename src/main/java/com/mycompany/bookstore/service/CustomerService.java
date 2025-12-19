package com.mycompany.bookstore.service;

import com.mycompany.bookstore.dao.CustomerDAO;
import com.mycompany.bookstore.model.Customer;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void addCustomer(Customer customer) throws Exception {
        if (customer.getFirstName() == null || customer.getFirstName().isEmpty()) {
            throw new Exception("First name cannot be empty");
        }
        if (customer.getUserName() == null || customer.getUserName().isEmpty()) {
            throw new Exception("Username cannot be empty");
        }
        customerDAO.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) throws Exception {
        if (customer.getUserId() <= 0) {
            throw new Exception("Invalid customer ID");
        }
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(int userId) throws Exception {
        if (userId <= 0) {
            throw new Exception("Invalid customer ID");
        }
        customerDAO.deleteCustomer(userId);
    }

    public Customer getCustomerById(int userId) {
        return customerDAO.getCustomerById(userId);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
}
