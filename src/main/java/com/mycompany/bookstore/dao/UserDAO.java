package com.mycompany.bookstore.dao;

import com.mycompany.bookstore.model.Users;

public interface UserDAO {
    Users login(String username, String password);
}
