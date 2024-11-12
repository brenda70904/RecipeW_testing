package reviewApp.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import reviewApp.model.*;

public class FoodCartRestaurantsDao extends RestaurantsDao {
	
	private static FoodCartRestaurantsDao instance = null;

	protected FoodCartRestaurantsDao() {
		super();
	}

	public static FoodCartRestaurantsDao getInstance() {
		if (instance == null) {
			instance = new FoodCartRestaurantsDao();
		}
		return instance;
	}

	public FoodCartRestaurants create(FoodCartRestaurants foodCartRestaurant) throws SQLException {
		// Insert into the Restaurants table first.
		Restaurants restaurant = super.create(new Restaurants(
				foodCartRestaurant.getName(), foodCartRestaurant.getDescription(),
				foodCartRestaurant.getMenu(), foodCartRestaurant.getHours(),
				foodCartRestaurant.isActive(), foodCartRestaurant.getCuisineType(),
				foodCartRestaurant.getStreet1(), foodCartRestaurant.getStreet2(),
				foodCartRestaurant.getCity(), foodCartRestaurant.getState(),
				foodCartRestaurant.getZip(), foodCartRestaurant.getCompanyName()
		));

		String insertFoodCartRestaurant = "INSERT INTO FoodCartRestaurant(RestaurantId, Licensed) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFoodCartRestaurant);

			// Use the generated RestaurantId from the Restaurants table for the FoodCartRestaurant table.
			insertStmt.setInt(1, restaurant.getRestaurantId());
			insertStmt.setBoolean(2, foodCartRestaurant.isLicensed());
			insertStmt.executeUpdate();

			// Set the RestaurantId for the FoodCartRestaurants object.
			foodCartRestaurant.setRestaurantId(restaurant.getRestaurantId());
			return foodCartRestaurant;
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
		}
	}

	public FoodCartRestaurants getFoodCartRestaurantById(int foodCartRestaurantId) throws SQLException {
		String selectFoodCartRestaurant =
			"SELECT Restaurants.RestaurantId AS RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, " +
			"Street1, Street2, City, State, Zip, CompanyName, Licensed " +
			"FROM FoodCartRestaurant INNER JOIN Restaurants " +
			"ON FoodCartRestaurant.RestaurantId = Restaurants.RestaurantId " +
			"WHERE FoodCartRestaurant.RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFoodCartRestaurant);
			selectStmt.setInt(1, foodCartRestaurantId);
			results = selectStmt.executeQuery();
			if (results.next()) {
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
				boolean licensed = results.getBoolean("Licensed");

				FoodCartRestaurants foodCartRestaurant = new FoodCartRestaurants(
						restaurantId, name, description, menu, hours, active, cuisineType,
						street1, street2, city, state, zip, companyName, licensed);
				return foodCartRestaurant;
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

	public List<FoodCartRestaurants> getFoodCartRestaurantsByCompanyName(String companyName) throws SQLException {
		List<FoodCartRestaurants> foodCartRestaurants = new ArrayList<>();
		String selectFoodCartRestaurants =
			"SELECT Restaurants.RestaurantId AS RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, " +
			"Street1, Street2, City, State, Zip, CompanyName, Licensed " +
			"FROM FoodCartRestaurant INNER JOIN Restaurants " +
			"ON FoodCartRestaurant.RestaurantId = Restaurants.RestaurantId " +
			"WHERE Restaurants.CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFoodCartRestaurants);
			selectStmt.setString(1, companyName);
			results = selectStmt.executeQuery();
			while (results.next()) {
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
				boolean licensed = results.getBoolean("Licensed");

				FoodCartRestaurants foodCartRestaurant = new FoodCartRestaurants(
						restaurantId, name, description, menu, hours, active, cuisineType,
						street1, street2, city, state, zip, companyName, licensed);
				foodCartRestaurants.add(foodCartRestaurant);
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
		return foodCartRestaurants;
	}

	public FoodCartRestaurants delete(FoodCartRestaurants foodCartRestaurant) throws SQLException {
		String deleteFoodCartRestaurant = "DELETE FROM FoodCartRestaurant WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFoodCartRestaurant);
			deleteStmt.setInt(1, foodCartRestaurant.getRestaurantId());
			deleteStmt.executeUpdate();

			// Delete from the superclass (Restaurants) table.
			super.delete(foodCartRestaurant);

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
