 package com.mycompany.bookstore.service;

import com.mycompany.bookstore.dao.ReviewDAO;
import com.mycompany.bookstore.daoImp.ReviewDAOimp;
import com.mycompany.bookstore.model.Review;

import java.sql.Connection;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReviewService {

    private ReviewDAO reviewDAO;

    public ReviewService(Connection connection) {
        this.reviewDAO = new ReviewDAOimp(connection);
    }

    public void addReview(Review review) {

        if (review == null)
            throw new IllegalArgumentException("Review cannot be null");

        if (review.getBook() == null || review.getBook().getBookId() == null)
            throw new IllegalArgumentException("Review must be linked to a book");

        if (review.getCustomer() == null || review.getCustomer().getUserId() <= 0)
            throw new IllegalArgumentException("Review must be linked to a customer");

        if (review.getRating() == null ||
            review.getRating().compareTo(BigDecimal.ONE) < 0 ||
            review.getRating().compareTo(BigDecimal.valueOf(5)) > 0)
            throw new IllegalArgumentException("Rating must be between 1 and 5");

        if (review.getReviewDate() == null)
            review.setReviewDate(LocalDate.now());

        reviewDAO.addReview(review);
    }

    public void updateReview(Review review) {

        if (review == null)
            throw new IllegalArgumentException("Review cannot be null");

        if (review.getReviewId() <= 0)
            throw new IllegalArgumentException("Review must have valid id");

        reviewDAO.updateReview(review);
    }

    public void deleteReview(int reviewId) {

        if (reviewId <= 0)
            throw new IllegalArgumentException("ReviewId must be > 0");

        reviewDAO.deleteReview(reviewId);
    }

    public Review getReviewById(int reviewId) {

        if (reviewId <= 0)
            throw new IllegalArgumentException("ReviewId must be > 0");

        return reviewDAO.getReviewById(reviewId);
    }

    public List<Review> getReviewsByBookId(String bookId) {

        if (bookId == null || bookId.isBlank())
            throw new IllegalArgumentException("BookId is required");

        return reviewDAO.getReviewsByBookId(bookId);
    }

    public List<Review> getAllReviews() {
        return reviewDAO.getAllReviews();
    }
}
