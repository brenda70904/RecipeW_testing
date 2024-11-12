package reviewApp.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import reviewApp.model.*;

public class ReviewsDao {

	private static ReviewsDao instance = null;
	protected ConnectionManager connectionManager;

	protected ReviewsDao() {
		connectionManager = new ConnectionManager();
	}

	public static ReviewsDao getInstance() {
		if (instance == null) {
			instance = new ReviewsDao();
		}
		return instance;
	}

	// Create a new review
	public Reviews create(Reviews review) throws SQLException {
		String insertReview = "INSERT INTO Reviews(Content, Rating, UserName, RestaurantId) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReview, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, review.getContent());
			insertStmt.setDouble(2, review.getRating());
			insertStmt.setString(3, review.getUserName());
			insertStmt.setInt(4, review.getRestaurantId());
			insertStmt.executeUpdate();

			// Retrieve the auto-generated key for reviewId.
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if (resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}

			// Set the reviewId and created timestamp on the Review object before returning.
			review.setReviewId(reviewId);
			return review;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (insertStmt != null) {
				insertStmt.close();
			}
			if (resultKey != null) {
				resultKey.close();
			}
		}
	}

	// Get a review by ID
	public Reviews getReviewById(int reviewId) throws SQLException {
		String selectReview = "SELECT ReviewId, Created, Content, Rating, UserName, RestaurantId FROM Reviews WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, reviewId);
			results = selectStmt.executeQuery();
			if (results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				Timestamp created = results.getTimestamp("Created");
				String content = results.getString("Content");
				double rating = results.getDouble("Rating");
				String userName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");

				Reviews review = new Reviews(resultReviewId, created, content, rating, userName, restaurantId);
				return review;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return null;
	}

	// Get reviews by user name
	public List<Reviews> getReviewsByUserName(String userName) throws SQLException {
		List<Reviews> reviews = new ArrayList<>();
		String selectReviews = "SELECT ReviewId, Created, Content, Rating, UserName, RestaurantId FROM Reviews WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int reviewId = results.getInt("ReviewId");
				Timestamp created = results.getTimestamp("Created");
				String content = results.getString("Content");
				double rating = results.getDouble("Rating");
				int restaurantId = results.getInt("RestaurantId");

				Reviews review = new Reviews(reviewId, created, content, rating, userName, restaurantId);
				reviews.add(review);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return reviews;
	}

	// Get reviews by restaurant ID
	public List<Reviews> getReviewsByRestaurantId(int restaurantId) throws SQLException {
		List<Reviews> reviews = new ArrayList<>();
		String selectReviews = "SELECT ReviewId, Created, Content, Rating, UserName, RestaurantId FROM Reviews WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int reviewId = results.getInt("ReviewId");
				Timestamp created = results.getTimestamp("Created");
				String content = results.getString("Content");
				double rating = results.getDouble("Rating");
				String userName = results.getString("UserName");

				Reviews review = new Reviews(reviewId, created, content, rating, userName, restaurantId);
				reviews.add(review);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return reviews;
	}

	// Delete a review
	public Reviews delete(Reviews review) throws SQLException {
		String deleteReview = "DELETE FROM Reviews WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer use the review instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
}
