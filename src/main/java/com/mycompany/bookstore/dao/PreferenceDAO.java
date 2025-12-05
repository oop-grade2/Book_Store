package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.Preference;
import java.util.List;

public interface PreferenceDAO {
    void addPreference(Preference preference);
    Preference getPreferenceByUserId(int userId);
    void updatePreference(Preference preference);
    void deletePreference(int preferenceId);
    List<Preference> getAllPreferences();
}
