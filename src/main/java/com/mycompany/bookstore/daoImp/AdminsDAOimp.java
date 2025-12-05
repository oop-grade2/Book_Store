package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.AdminsDAO;
import com.mycompany.bookstore.model.Admins;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminsDAOimp implements AdminsDAO {

    private Connection connection;

    public AdminsDAOimp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addAdmin(Admins admin) {
        String sqlUsers = "INSERT INTO Users (FirstName, LastName, PhoneNumber, "
                + "Email, UserName, PasswordHash, UserType, AccDateUpdated) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlAdmins = "INSERT INTO Admins (UserID, [Role], Department) VALUES (?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sqlUsers, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, admin.getFirstName());
                statement.setString(2, admin.getLastName());
                statement.setString(3, admin.getPhoneNumber());
                statement.setString(4, admin.getEmail());
                statement.setString(5, admin.getUserName());
                statement.setString(6, admin.getPasswordHash());
                statement.setString(7, "ADMIN");

                if (admin.getAccDateUpdated() != null) {
                    statement.setTimestamp(8, Timestamp.valueOf(admin.getAccDateUpdated().atStartOfDay()));
                } else {
                    statement.setNull(8, Types.TIMESTAMP);
                }

                statement.executeUpdate();

                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        int generatedUserId = keys.getInt(1);
                        admin.setUserId(generatedUserId);

                        try (PreparedStatement statement2 = connection.prepareStatement(sqlAdmins)) {
                            statement2.setInt(1, generatedUserId);
                            statement2.setString(2, admin.getRole());
                            statement2.setString(3, admin.getDepartment());
                            statement2.executeUpdate();
                        }
                    } else {
                        throw new SQLException("User ID not generated");
                    }
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("Error! Cannot add admin.");
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
    public void updateAdmin(Admins admin) {
        String sqlUsers = "UPDATE Users SET FirstName=?, LastName=?, PhoneNumber=?, "
                + "Email=?, UserName=?, PasswordHash=?, AccDateUpdated=? WHERE UserID=?";
        String sqlAdmins = "UPDATE Admins SET Department=?, [Role]=? WHERE UserID=?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sqlUsers)) {
                statement.setString(1, admin.getFirstName());
                statement.setString(2, admin.getLastName());
                statement.setString(3, admin.getPhoneNumber());
                statement.setString(4, admin.getEmail());
                statement.setString(5, admin.getUserName());
                statement.setString(6, admin.getPasswordHash());

                if (admin.getAccDateUpdated() != null) {
                    statement.setTimestamp(7, Timestamp.valueOf(admin.getAccDateUpdated().atStartOfDay()));
                } else {
                    statement.setNull(7, Types.TIMESTAMP);
                }

                statement.setInt(8, admin.getUserId());
                statement.executeUpdate();
            }

            try (PreparedStatement statement2 = connection.prepareStatement(sqlAdmins)) {
                statement2.setString(1, admin.getDepartment());
                statement2.setString(2, admin.getRole());
                statement2.setInt(3, admin.getUserId());
                statement2.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Error! Cannot update admin.");
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
    public void deleteAdmin(int userId) {
        String sqlAdmins = "DELETE FROM Admins WHERE UserID=?";
        String sqlUsers = "DELETE FROM Users WHERE UserID=?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sqlAdmins)) {
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
            System.out.println("Error! Cannot delete admin.");
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
    public Admins getAdminById(int userId) {
        String sql = "SELECT u.UserID, u.FirstName, u.LastName, u.PhoneNumber, u.Email, u.UserName, "
                + "u.PasswordHash, u.AccDateCreated, u.AccDateUpdated, "
                + "a.Department, a.Role "
                + "FROM Users u "
                + "JOIN Admins a "
                + "ON u.UserID = a.UserID "
                + "WHERE u.UserID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet res = statement.executeQuery();

            if (res.next()) {
                return convertToAdmin(res);
            }
        } catch (SQLException e) {
            System.out.println("Error! Cannot get admin.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Admins> getAllAdmins() {
        List<Admins> admins = new ArrayList<>();
        String sql = "SELECT u.UserID, u.FirstName, u.LastName, u.PhoneNumber, u.Email, u.UserName, "
                + "u.PasswordHash, u.AccDateCreated, u.AccDateUpdated, "
                + "a.Department, a.Role "
                + "FROM Users u "
                + "JOIN Admins a "
                + "ON u.UserID = a.UserID";

        try (Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery(sql);
            while (res.next()) {
                admins.add(convertToAdmin(res));
            }
        } catch (SQLException e) {
            System.out.println("Error! Cannot get all admins.");
            e.printStackTrace();
        }
        return admins;
    }

    private Admins convertToAdmin(ResultSet res) throws SQLException {
        Admins admin = new Admins();
        admin.setUserId(res.getInt("UserID"));
        admin.setFirstName(res.getString("FirstName"));
        admin.setLastName(res.getString("LastName"));
        admin.setPhoneNumber(res.getString("PhoneNumber"));
        admin.setEmail(res.getString("Email"));
        admin.setUserName(res.getString("UserName"));
        admin.setStoredPasswordHash(res.getString("PasswordHash"));

        Date accCreated = res.getDate("AccDateCreated");
        if (accCreated != null) admin.setAccDateCreated(accCreated.toLocalDate());

        Date accUpdated = res.getDate("AccDateUpdated");
        if (accUpdated != null) admin.setAccDateUpdated(accUpdated.toLocalDate());

        admin.setDepartment(res.getString("Department"));
        admin.setRole(res.getString("Role"));

        return admin;
    }
}