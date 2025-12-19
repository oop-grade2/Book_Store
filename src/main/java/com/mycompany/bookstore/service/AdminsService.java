package com.mycompany.bookstore.service;
//flgfdg
import com.mycompany.bookstore.dao.AdminsDAO;
import com.mycompany.bookstore.model.Admins;
import java.util.List;

public class AdminsService {
    private AdminsDAO adminsDAO;

    public AdminsService(AdminsDAO adminsDAO) {
        this.adminsDAO = adminsDAO;
    }

    public void addAdmin(Admins admin) throws Exception {

        if (admin.getFirstName() == null || admin.getFirstName().isEmpty()) {
            throw new Exception("First name can not be empty");
        }
        if (admin.getUserName() == null || admin.getUserName().isEmpty()) {
            throw new Exception("Username can not be empty");
        }

        adminsDAO.addAdmin(admin);
    }

    public void updateAdmin(Admins admin) throws Exception {
        if (admin.getUserId() <= 0) {
            throw new Exception("Invalid admin ID");
        }
        adminsDAO.updateAdmin(admin);
    }

    public void deleteAdmin(int userId) throws Exception {
        if (userId <= 0) {
            throw new Exception("Invalid admin ID");
        }
        adminsDAO.deleteAdmin(userId);
    }

    public Admins getAdminById(int userId) {
        return adminsDAO.getAdminById(userId);
    }

    public List<Admins> getAllAdmins() {
        return adminsDAO.getAllAdmins();
    }
}
