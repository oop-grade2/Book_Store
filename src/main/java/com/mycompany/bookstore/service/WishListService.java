package com.mycompany.bookstore.service;

import com.mycompany.bookstore.dao.WishListDAO;
import com.mycompany.bookstore.model.WishList;

import java.time.LocalDate;
import java.util.List;

public class WishListService {

    private final WishListDAO wishListDAO;

    public WishListService(WishListDAO wishListDAO) {
        this.wishListDAO = wishListDAO;
    }


    public void addWishListItem(WishList item) {
        if (item == null || item.getCustomer() == null || item.getBook() == null) {
            throw new IllegalArgumentException("WishList item must have a customer and a book");
        }

        // If dateAdded is null set it to today
        if (item.getDateAdded() == null) {
            item.setDateAdded(LocalDate.now());
        }

        wishListDAO.addWishListItem(item);
    }

    public void deleteWishListItem(int userId, String bookId) {
        if (userId <= 0 || bookId == null || bookId.isEmpty()) {
            throw new IllegalArgumentException("Invalid userId or bookId");
        }
        wishListDAO.deleteWishListItem(userId, bookId);
    }


    public List<WishList> getWishListByUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid userId");
        }
        return wishListDAO.getWishListByUserId(userId);
    }
}
