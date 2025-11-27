package com.mycompany.bookstore.dao;
import com.mycompany.bookstore.model.Review;
import java.util.List;

public interface ReviewDAO {
    void addReview(Review review);
    void updateReview(Review review);
    void deleteReview(int reviewId);
    Review getReviewById(int reviewId);
    List<Review> getReviewsByBookId(String bookId);
    List<Review> getAllReviews();
}
