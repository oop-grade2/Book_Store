package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.Preference;

public interface PreferenceDAO {
    void addPreference(Preference preference);
    Preference getPreferenceByUserId(int userId);
    void updatePreference(Preference preference);
    void deletePreference(int preferenceId);
}
