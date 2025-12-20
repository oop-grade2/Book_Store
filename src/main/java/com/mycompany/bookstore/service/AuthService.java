package com.mycompany.bookstore.service;

import com.mycompany.bookstore.dao.UserDAO;
import com.mycompany.bookstore.daoImp.UserDAOimp;
import com.mycompany.bookstore.model.Users;
import com.mycompany.bookstore.util.DBConnection;

public class AuthService {

    private UserDAO userDAO;

    public AuthService() {
        userDAO = new UserDAOimp(DBConnection.getConnection());
    }

    public Users login(String username, String password) {
        return userDAO.login(username, password);
    }
}
