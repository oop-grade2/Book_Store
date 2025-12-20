package com.mycompany.bookstore.daoImp;
import com.mycompany.bookstore.dao.CustomerDAO;
import com.mycompany.bookstore.model.Customer;
import com.mycompany.bookstore.util.PasswordUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CustomerDAOimp implements CustomerDAO {
    private Connection connection;
    public CustomerDAOimp(Connection connection) {
        this.connection = connection;
    }
    @Override
public void addCustomer(Customer customer) {

    String sqlUsers =
        "INSERT INTO Users (FirstName, LastName, PhoneNumber, Email, " +
        "UserName, PasswordHash, UserType, AccDateUpdated) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    String sqlCustomer =
        "INSERT INTO Customer (UserID, CustomerAddress, CommunicationPrefrence) " +
        "VALUES (?, ?, ?)";

    try {
        connection.setAutoCommit(false);

        try (PreparedStatement ps =
                     connection.prepareStatement(sqlUsers, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getPhoneNumber());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getUserName());

            // ✅ الهاش جاي جاهز من الموديل
            ps.setString(6, customer.getPasswordHash());

            ps.setString(7, "CUSTOMER");
            ps.setTimestamp(8, Timestamp.valueOf(java.time.LocalDateTime.now()));

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int userId = keys.getInt(1);
                customer.setUserId(userId);

                try (PreparedStatement ps2 =
                             connection.prepareStatement(sqlCustomer)) {

                    ps2.setInt(1, userId);
                    ps2.setNull(2, Types.VARCHAR);
                    ps2.setNull(3, Types.VARCHAR);
                    ps2.executeUpdate();
                }
            }
        }

        connection.commit();

    } catch (SQLException e) {
        try { connection.rollback(); } catch (SQLException ex) {}
        e.printStackTrace();
    } finally {
        try { connection.setAutoCommit(true); } catch (SQLException ex) {}
    }
}


    @Override
    public void updateCustomer(Customer customer) {
        // تحديث البيانات الأساسية
        String sqlUsers = "UPDATE Users SET FirstName=?, LastName=?, PhoneNumber=?, "
                + "Email=?, UserName=?, PasswordHash=?, AccDateUpdated=? WHERE UserID=?";
        
        // تحديث بيانات العميل الخاصة
        String sqlCustomer = "UPDATE Customer SET CustomerAddress=?, CommunicationPreference=? WHERE UserID=?";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sqlUsers)) {
                statement.setString(1, customer.getFirstName());
                statement.setString(2, customer.getLastName());
                statement.setString(3, customer.getPhoneNumber());
                statement.setString(4, customer.getEmail());
                statement.setString(5, customer.getUserName());
                statement.setString(6, customer.getPasswordHash());
                
                if (customer.getAccDateUpdated() != null) {
                    statement.setTimestamp(7, Timestamp.valueOf(customer.getAccDateUpdated().atStartOfDay()));
                } else {
                    statement.setNull(7, Types.TIMESTAMP);
                }
                statement.setInt(8, customer.getUserId());
                statement.executeUpdate();
            }
            try (PreparedStatement statement2 = connection.prepareStatement(sqlCustomer)) {
                statement2.setString(1, customer.getCustomerAddress());
                statement2.setString(2, customer.getCommunicationPreference());
                statement2.setInt(3, customer.getUserId());
                statement2.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Error! Cannot update customer.");
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    @Override
    public void deleteCustomer(int userId) {
        // لازم نحذف من الجدول الفرعي (Customers) الأول وبعدين الرئيسي (Users)
        String sqlCustomer = "DELETE FROM Customer WHERE UserID=?";
        String sqlUsers = "DELETE FROM Users WHERE UserID=?";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sqlCustomer)) {
                statement.setInt(1, userId);
                statement.executeUpdate();
            }
            try (PreparedStatement statement2 = connection.prepareStatement(sqlUsers)) {
                statement2.setInt(1, userId);
                statement2.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Error! Cannot delete customer.");
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    @Override
    public Customer getCustomerById(int userId) {
        // لازم نعمل JOIN عشان نجيب البيانات من الجدولين
        String sql = "SELECT u.UserID, u.FirstName, u.LastName, u.PhoneNumber, u.Email, u.UserName, "
                + "u.PasswordHash, u.AccDateCreated, u.AccDateUpdated, "
                + "c.CustomerAddress, c.CommunicationPreference "
                + "FROM Users u "
                + "JOIN Customer c ON u.UserID = c.UserID "
                + "WHERE u.UserID=?";
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
        // JOIN عشان نجيب كل العملاء ببياناتهم الكاملة
        String sql = "SELECT u.UserID, u.FirstName, u.LastName, u.PhoneNumber, u.Email, u.UserName, "
                + "u.PasswordHash, u.AccDateCreated, u.AccDateUpdated, "
                + "c.CustomerAddress, c.CommunicationPreference "
                + "FROM Users u "
                + "JOIN Customer c ON u.UserID = c.UserID";
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
    private Customer convertToCustomer(ResultSet rs) throws SQLException {

        Customer customer = new Customer();

        customer.setUserId(rs.getInt("UserID"));
        customer.setFirstName(rs.getString("FirstName"));
        customer.setLastName(rs.getString("LastName"));
        customer.setPhoneNumber(rs.getString("PhoneNumber"));
        customer.setEmail(rs.getString("Email"));
        customer.setUserName(rs.getString("UserName"));
        customer.setStoredPasswordHash(rs.getString("PasswordHash"));

        Date created = rs.getDate("AccDateCreated");
        if (created != null)
            customer.setAccDateCreated(created.toLocalDate());

        Date updated = rs.getDate("AccDateUpdated");
        if (updated != null)
            customer.setAccDateUpdated(updated.toLocalDate());

        customer.setCustomerAddress(rs.getString("CustomerAddress"));
        customer.setCommunicationPreference(rs.getString("CommunicationPrefrence"));

        return customer;
    }

   @Override
public Customer login(String username, String password) {

    String sql =
        "SELECT u.*, c.CustomerAddress, c.CommunicationPrefrence " +
        "FROM Users u LEFT JOIN Customer c ON u.UserID = c.UserID " +
        "WHERE u.UserName=? AND u.UserType='CUSTOMER'";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String storedHash = rs.getString("PasswordHash");

            if (PasswordUtil.checkPassword(password, storedHash)) {
                return convertToCustomer(rs);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}

}