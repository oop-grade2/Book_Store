package com.mycompany.bookstore.service;

import com.mycompany.bookstore.daoImp.PreferenceDAOimp;
import com.mycompany.bookstore.model.Preference;

import java.sql.Connection;

public class PreferenceService {

    private PreferenceDAOimp preferenceDAO;

    public PreferenceService(Connection connection) {
        this.preferenceDAO = new PreferenceDAOimp(connection);
    }

    public void savePreference(Preference preference) {

        if (preference == null)
            throw new IllegalArgumentException("Preference is null");

        if (preference.getCustomer() == null || preference.getCustomer().getUserId() <= 0)
            throw new IllegalArgumentException("Preference must be linked to customer");

        if (preference.getFavoriteGenre() == null)
            preference.setFavoriteGenre("General");

        preferenceDAO.addPreference(preference);
    }

    public Preference getCustomerPreference(int userId) {
        return preferenceDAO.getPreferenceByUserId(userId);
    }
}
