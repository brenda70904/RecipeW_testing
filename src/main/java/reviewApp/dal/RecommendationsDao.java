package reviewApp.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import reviewApp.model.*;

public class RecommendationsDao {
	
	private static RecommendationsDao instance = null;
	protected ConnectionManager connectionManager;

	protected RecommendationsDao() {
		connectionManager = new ConnectionManager();
	}

	public static RecommendationsDao getInstance() {
		if (instance == null) {
			instance = new RecommendationsDao();
		}
		return instance;
	}

	// Create a new recommendation
	public Recommendations create(Recommendations recommendation) throws SQLException {
		String insertRecommendation = "INSERT INTO Recommendations(UserName, RestaurantId) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRecommendation, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, recommendation.getUserName());
			insertStmt.setInt(2, recommendation.getRestaurantId());
			insertStmt.executeUpdate();

			// Retrieve the auto-generated key for recommendationId.
			resultKey = insertStmt.getGeneratedKeys();
			int recommendationId = -1;
			if (resultKey.next()) {
				recommendationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}

			// Set the recommendationId on the recommendation object before returning.
			recommendation.setRecommendationId(recommendationId);
			return recommendation;
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

	public Recommendations getRecommendationById(int recommendationId) throws SQLException {
		String selectRecommendation = "SELECT RecommendationId, UserName, RestaurantId FROM Recommendations WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendation);
			selectStmt.setInt(1, recommendationId);
			results = selectStmt.executeQuery();
			if (results.next()) {
				int resultRecommendationId = results.getInt("RecommendationId");
				String userName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");

				Recommendations recommendation = new Recommendations(resultRecommendationId, userName, restaurantId);
				return recommendation;
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

	public List<Recommendations> getRecommendationsByUserName(String userName) throws SQLException {
		List<Recommendations> recommendations = new ArrayList<>();
		String selectRecommendations = "SELECT RecommendationId, UserName, RestaurantId FROM Recommendations WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendations);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int recommendationId = results.getInt("RecommendationId");
				int restaurantId = results.getInt("RestaurantId");

				Recommendations recommendation = new Recommendations(recommendationId, userName, restaurantId);
				recommendations.add(recommendation);
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
		return recommendations;
	}

	public List<Recommendations> getRecommendationsByRestaurantId(int restaurantId) throws SQLException {
		List<Recommendations> recommendations = new ArrayList<>();
		String selectRecommendations = "SELECT RecommendationId, UserName, RestaurantId FROM Recommendations WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendations);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int recommendationId = results.getInt("RecommendationId");
				String userName = results.getString("UserName");

				Recommendations recommendation = new Recommendations(recommendationId, userName, restaurantId);
				recommendations.add(recommendation);
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
		return recommendations;
	}

	// Delete a recommendation
	public Recommendations delete(Recommendations recommendation) throws SQLException {
		String deleteRecommendation = "DELETE FROM Recommendations WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRecommendation);
			deleteStmt.setInt(1, recommendation.getRecommendationId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer use the recommendation instance.
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
