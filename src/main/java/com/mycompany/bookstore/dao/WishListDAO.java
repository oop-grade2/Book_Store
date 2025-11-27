package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.WishList;
import java.util.List;

public interface WishListDAO {
    void addWishListItem(WishList item);
    void deleteWishListItem(int userId, String bookId);
    List<WishList> getWishListByUserId(int userId);
}
