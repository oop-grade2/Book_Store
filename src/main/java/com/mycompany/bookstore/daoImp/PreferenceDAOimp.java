package com.mycompany.bookstore.daoImp;

import com.mycompany.bookstore.dao.PreferenceDAO;
import com.mycompany.bookstore.model.Customer;
import com.mycompany.bookstore.model.Preference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PreferenceDAOimp implements PreferenceDAO {

    private final Connection connection;

    public PreferenceDAOimp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addPreference(Preference preference) {
        String sql = "INSERT INTO Preference (FavoriteGenre, FavoriteAuthor, Recommendations, UserID) "
                   + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, preference.getFavoriteGenre());
            statement.setString(2, preference.getFavoriteAuthor());
            statement.setString(3, preference.getRecommendations());
            statement.setInt(4, preference.getCustomer().getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in addPreference()");
            e.printStackTrace();
        }
    }

    @Override
    public Preference getPreferenceByUserId(int userId) {
        String sql = "SELECT * FROM Preference WHERE UserID = ?";
        Preference preference = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    preference = ConvertToPreference(res, userId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in getPreferenceByUserId()");
            e.printStackTrace();
        }

        return preference;
    }

    @Override
    public void updatePreference(Preference preference) {
        
        String sql = "UPDATE Preference SET FavoriteGenre = ?, FavoriteAuthor = ?, Recommendations = ? "
                   + "WHERE PreferenceID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, preference.getFavoriteGenre());
            statement.setString(2, preference.getFavoriteAuthor());
            statement.setString(3, preference.getRecommendations());
            statement.setInt(4, preference.getPreferenceId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in updatePreference()");
            e.printStackTrace();
        }
    }

    @Override
    public void deletePreference(int preferenceId) {
        String sql = "DELETE FROM Preference WHERE PreferenceID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, preferenceId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in deletePreference()");
            e.printStackTrace();
        }
    }

    @Override
    public List<Preference> getAllPreferences() {
        String sql = "SELECT * FROM Preference";
        List<Preference> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet res = statement.executeQuery()) {

            while (res.next()) {
                int userId = res.getInt("UserID");
                list.add(ConvertToPreference(res, userId));
            }

        } catch (SQLException e) {
            System.out.println("Error in getAllPreferences()");
            e.printStackTrace();
        }

        return list;
    }

    
    private Preference ConvertToPreference(ResultSet res, int userId) throws SQLException {
        Preference preference = new Preference();
        preference.setPreferenceId(res.getInt("PreferenceID"));
        preference.setFavoriteGenre(res.getString("FavoriteGenre"));
        preference.setFavoriteAuthor(res.getString("FavoriteAuthor"));
        preference.setRecommendations(res.getString("Recommendations"));

        Customer customer = new Customer();
        customer.setUserId(userId);
        preference.setCustomer(customer);

        return preference;
    }
}
