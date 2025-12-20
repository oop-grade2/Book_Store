package com.mycompany.bookstore.model;
import com.mycompany.bookstore.util.PasswordUtil;
import java.time.LocalDate;


public abstract class Users {
    protected int userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String userName;
    private String passwordHash;
    private LocalDate accDateCreated;
    private LocalDate accDateUpdated;
    protected String userType; 

    
    private boolean mustChangePassword = false;

//    public Users(int userId, String firstName, String lastName, String phoneNumber, 
//            String email, String userName, String passwordPlain, LocalDate accDateCreated, 
//            LocalDate accDateUpdated) {
//        this.userId = userId;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.userName = userName;
//        this.passwordHash = PasswordUtil.hashPassword(passwordPlain);
//        this.accDateCreated = accDateCreated;
//        this.accDateUpdated = accDateUpdated;
//    }
//    
    
    
    public Users(int userId, String firstName, String lastName, String phoneNumber, 
        String email, String userName, LocalDate accDateCreated, 
        LocalDate accDateUpdated) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.userName = userName;
    this.accDateCreated = accDateCreated;
    this.accDateUpdated = accDateUpdated;
}

    public Users() {
        this.userId = 0;
        this.firstName = "";
        this.lastName = "";
        this.phoneNumber = "";
        this.email = "";
        this.userName = "";
        this.passwordHash = "";
        this.accDateCreated = null;
        this.accDateUpdated = null;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    

    // عشان اليوزر يتنيل يغيرها ف هتتشفر
    public void setPlainPassword(String plainPassword) {
        this.passwordHash = PasswordUtil.hashPassword(plainPassword);
    }

    //  دي لو عايزين نجيبها من الداتابيبز ف عشان منشفرهاش مرتين
    public void setStoredPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }

    public LocalDate getAccDateCreated() {
        return accDateCreated;
    }

    public void setAccDateCreated(LocalDate accDateCreated) {
        this.accDateCreated = accDateCreated;
    }

    public LocalDate getAccDateUpdated() {
        return accDateUpdated;
    }

    public void setAccDateUpdated(LocalDate accDateUpdated) {
        this.accDateUpdated = accDateUpdated;
    }
       
    public boolean checkPassword(String plainPassword) {
        if (this.passwordHash == null || this.passwordHash.isEmpty()) 
            return false;
        
        return PasswordUtil.checkPassword(plainPassword, this.passwordHash);
    }
    
    public boolean getMustChangePassword() {
        return mustChangePassword; 
    }
    
    public void setMustChangePassword(boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword; 
    }
}
