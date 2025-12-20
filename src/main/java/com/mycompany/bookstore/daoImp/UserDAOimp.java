package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.UserDAO;
import com.mycompany.bookstore.model.Admins;
import com.mycompany.bookstore.model.Customer;
import com.mycompany.bookstore.model.Users;
import com.mycompany.bookstore.util.PasswordUtil;

import java.sql.*;

public class UserDAOimp implements UserDAO {

    private Connection connection;

    public UserDAOimp(Connection connection) {
        this.connection = connection;
    }
@Override
public Users login(String username, String password) {
    String sql = "SELECT * FROM Users WHERE UserName = ?";
    
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String storedHash = rs.getString("PasswordHash");

            Users user;
            String userType = rs.getString("UserType");

            if ("ADMIN".equalsIgnoreCase(userType)) {
                user = new Admins();
            } else {
                user = new Customer();
            }

            user.setUserId(rs.getInt("UserID"));
            user.setUserName(rs.getString("UserName"));
            user.setStoredPasswordHash(storedHash);

            if (user.checkPassword(password)) {
                return user;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

}


