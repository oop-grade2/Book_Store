package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.Admins;
import java.util.List;

public interface AdminsDAO {
    void addAdmin(Admins admin);
    void updateAdmin(Admins admin);
    void deleteAdmin(int userId);
    Admins getAdminById(int userId);
    List<Admins> getAllAdmins();
}
