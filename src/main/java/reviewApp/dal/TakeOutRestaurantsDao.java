package reviewApp.dal;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import reviewApp.model.*;


public class TakeOutRestaurantsDao extends RestaurantsDao {
	
	private static TakeOutRestaurantsDao instance = null;
	protected TakeOutRestaurantsDao() {
		super();
	}
	public static TakeOutRestaurantsDao getInstance() {
		if(instance == null) {
			instance = new TakeOutRestaurantsDao();
		}
		return instance;
	}

	public TakeOutRestaurants create(TakeOutRestaurants takeOutRestaurant) throws SQLException {
		// Insert into the Restaurants table first.
		Restaurants restaurant = super.create(new Restaurants(
                takeOutRestaurant.getName(), takeOutRestaurant.getDescription(),
                takeOutRestaurant.getMenu(), takeOutRestaurant.getHours(),
                takeOutRestaurant.isActive(), takeOutRestaurant.getCuisineType(),
                takeOutRestaurant.getStreet1(), takeOutRestaurant.getStreet2(),
                takeOutRestaurant.getCity(), takeOutRestaurant.getState(),
                takeOutRestaurant.getZip(), takeOutRestaurant.getCompanyName()
        ));

		String insertTakeOutRestaurant = "INSERT INTO TakeOutRestaurant(RestaurantId, MaxWaitTime) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertTakeOutRestaurant);

			// Use the generated RestaurantId from the Restaurants table for the TakeOutRestaurant table.
			insertStmt.setInt(1, restaurant.getRestaurantId());
			insertStmt.setInt(2, takeOutRestaurant.getMaxWaitTime());
			insertStmt.executeUpdate();

			// Set the ID for the TakeOutRestaurants object.
			takeOutRestaurant.setRestaurantId(restaurant.getRestaurantId());
			return takeOutRestaurant;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}
	public TakeOutRestaurants getTakeOutRestaurantById(int takeOutRestaurantId) throws SQLException {
		String selectTakeOutRestaurant =
			"SELECT Restaurants.RestaurantId AS RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, " +
			"Street1, Street2, City, State, Zip, CompanyName, MaxWaitTime " +
			"FROM TakeOutRestaurant INNER JOIN Restaurants " +
			"ON TakeOutRestaurant.RestaurantId = Restaurants.RestaurantId " +
			"WHERE TakeOutRestaurant.RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTakeOutRestaurant);
			selectStmt.setInt(1, takeOutRestaurantId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int restaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.CuisineType cuisineType = Restaurants.CuisineType.valueOf(results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				String companyName = results.getString("CompanyName");
				int maxWaitTime = results.getInt("MaxWaitTime");

				TakeOutRestaurants takeOutRestaurant = new TakeOutRestaurants(restaurantId, name, description, menu, hours,
						active, cuisineType, street1, street2, city, state, zip, companyName, maxWaitTime);
				return takeOutRestaurant;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	public List<TakeOutRestaurants> getTakeOutRestaurantsByCompanyName(String companyName) throws SQLException {
		List<TakeOutRestaurants> takeOutRestaurants = new ArrayList<>();
		String selectTakeOutRestaurants =
			"SELECT Restaurants.RestaurantId AS RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, " +
			"Street1, Street2, City, State, Zip, CompanyName, MaxWaitTime " +
			"FROM TakeOutRestaurant INNER JOIN Restaurants " +
			"ON TakeOutRestaurant.RestaurantId = Restaurants.RestaurantId " +
			"WHERE Restaurants.CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTakeOutRestaurants);
			selectStmt.setString(1, companyName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int restaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.CuisineType cuisineType = Restaurants.CuisineType.valueOf(results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				int maxWaitTime = results.getInt("MaxWaitTime");

				TakeOutRestaurants takeOutRestaurant = new TakeOutRestaurants(restaurantId, name, description, menu, hours,
						active, cuisineType, street1, street2, city, state, zip, companyName, maxWaitTime);
				takeOutRestaurants.add(takeOutRestaurant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return takeOutRestaurants;
	}

	public TakeOutRestaurants delete(TakeOutRestaurants takeOutRestaurant) throws SQLException {
		String deleteTakeOutRestaurant = "DELETE FROM TakeOutRestaurant WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteTakeOutRestaurant);
			deleteStmt.setInt(1, takeOutRestaurant.getRestaurantId());
			deleteStmt.executeUpdate();

			// Also delete from the superclass (Restaurants) table.
			super.delete(takeOutRestaurant);

			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
}
