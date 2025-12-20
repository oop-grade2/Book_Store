package com.mycompany.bookstore.model;
import java.time.LocalDate;

public class Admins extends Users{
        private String department;
        private String role;

        
    public Admins() {
        super(); 
        this.department = "";
        this.role = "";
    }    
        
    public Admins(String department, String role, int userId, String firstName, String lastName, String phoneNumber, String email, String userName, String passwordPlain, LocalDate accDateCreated, LocalDate accDateUpdated) {
        super(userId, firstName, lastName, phoneNumber, email, userName,accDateCreated, accDateUpdated);
        this.department = department;
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }      

}
