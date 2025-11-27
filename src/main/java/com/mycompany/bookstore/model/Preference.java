package com.mycompany.bookstore.model;

public class Preference {
    private int preferenceId;
    private String favoriteGenre;
    private String favoriteAuthor;
    private String recommendations;

    private Customer customer; // many to one 

    public Preference() {
    }
    
    public Preference(String favoriteGenre, String favoriteAuthor, 
            String recommendations, Customer customer) {
        this.favoriteGenre = favoriteGenre;
        this.favoriteAuthor = favoriteAuthor;
        this.recommendations = recommendations;
        this.customer = customer;
    }
    
    public Preference(int preferenceId, String favoriteGenre, String favoriteAuthor,
            String recommendations, Customer customer) {
        this.preferenceId = preferenceId;
        this.favoriteGenre = favoriteGenre;
        this.favoriteAuthor = favoriteAuthor;
        this.recommendations = recommendations;
        this.customer = customer;
    }
    
    public int getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getFavoriteGenre() {
        return favoriteGenre;
    }

    public void setFavoriteGenre(String favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }

    public String getFavoriteAuthor() {
        return favoriteAuthor;
    }

    public void setFavoriteAuthor(String favoriteAuthor) {
        this.favoriteAuthor = favoriteAuthor;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
      
}
