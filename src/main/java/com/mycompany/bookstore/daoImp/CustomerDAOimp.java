package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.CustomerDAO;
import com.mycompany.bookstore.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOimp implements CustomerDAO  {
    
    private Connection connection;

    public CustomerDAOimp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO Customers (FirstName, LastName, PhoneNumber, Email, "
                + "UserName, PasswordHash, AccDateUpdated, CustomerAddress, CommunicationPreference) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getUserName());
            statement.setString(6, customer.getPasswordHash());

            if (customer.getAccDateUpdated() != null) {
                statement.setDate(7, Date.valueOf(customer.getAccDateUpdated()));
            } else {
                statement.setNull(7, Types.DATE);
            }

            statement.setString(8, customer.getCustomerAddress());
            statement.setString(9, customer.getCommunicationPreference());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    customer.setUserId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error! Cannot add customer.");
            e.printStackTrace();
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE Customers SET FirstName=?, LastName=?, PhoneNumber=?, Email=?, "
                + "UserName=?, PasswordHash=?, AccDateUpdated=?, CustomerAddress=?, CommunicationPreference=? "
                + "WHERE UserID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getUserName());
            statement.setString(6, customer.getPasswordHash());

            if (customer.getAccDateUpdated() != null) {
                statement.setDate(7, Date.valueOf(customer.getAccDateUpdated()));
            } else {
                statement.setNull(7, Types.DATE);
            }

            statement.setString(8, customer.getCustomerAddress());
            statement.setString(9, customer.getCommunicationPreference());
            statement.setInt(10, customer.getUserId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error! Cannot add customer.");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(int userId) {
        String sql = "DELETE FROM Customers WHERE UserID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error! Cannot add customer.");
            e.printStackTrace();
        }
    }

    @Override
    public Customer getCustomerById(int userId) {
        String sql = "SELECT * FROM Customers WHERE UserID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet res = statement.executeQuery();

            if (res.next()) {
                return convertToCustomer(res);
            }

        } catch (SQLException e) {
            System.out.println("Error in getCustomerById!");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customers";

        try (Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery(sql);

            while (res.next()) {
                list.add(convertToCustomer(res));
            }

        } catch (SQLException e) {
            System.out.println("Error in getAllCustomers!");
            e.printStackTrace();
        }

        return list;
    }

    private Customer convertToCustomer(ResultSet res) throws SQLException {
        Customer customer = new Customer();
        
        customer.setUserId(res.getInt("UserID"));
        customer.setFirstName(res.getString("FirstName"));
        customer.setLastName(res.getString("LastName"));
        customer.setPhoneNumber(res.getString("PhoneNumber"));
        customer.setEmail(res.getString("Email"));
        customer.setUserName(res.getString("UserName"));
        customer.setStoredPasswordHash(res.getString("PasswordHash"));

        Date updatedDate = res.getDate("AccDateUpdated");
        if (updatedDate != null) customer.setAccDateUpdated(updatedDate.toLocalDate());

        Date createdDate = res.getDate("AccDateCreated"); 
        if (createdDate != null) customer.setAccDateCreated(createdDate.toLocalDate());

        customer.setCustomerAddress(res.getString("CustomerAddress"));
        customer.setCommunicationPreference(res.getString("CommunicationPreference"));

        return customer;
    }
    
}
