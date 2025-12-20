package com.mycompany.bookstore.model;
import java.time.LocalDate;


public class Customer extends Users{
    private String customerAddress;
    private String communicationPreference;
    
    // private List<Orders> orders;
    // private List<Review> reviews;
    // private List<WishList> wishList;
    // private List<Preference> preferences;


    public Customer() {
        super(); 
        this.customerAddress = "";
        this.communicationPreference = "";
    }
    
    public Customer(int userId, String customerAddress, String communicationPreference 
            , String firstName, String lastName, String phoneNumber, 
            String email, String userName, String passwordPlain, LocalDate accDateCreated, 
            LocalDate accDateUpdated) {
        
        super(userId, firstName, lastName, phoneNumber, email, userName, 
                 accDateCreated, accDateUpdated);
        
        this.customerAddress = customerAddress;
        this.communicationPreference = communicationPreference;
        
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCommunicationPreference() {
        return communicationPreference;
    }

    public void setCommunicationPreference(String communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

}