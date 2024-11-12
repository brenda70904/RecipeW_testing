package reviewApp.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import reviewApp.model.*;

public class RestaurantsDao {
	protected ConnectionManager connectionManager;
	
	private static RestaurantsDao instance = null;
	protected RestaurantsDao() {
		connectionManager = new ConnectionManager();
	}
	public static RestaurantsDao getInstance() {
		if(instance == null) {
			instance = new RestaurantsDao();
		}
		return instance; 
	}
	
	public Restaurants create(Restaurants restaurant) throws SQLException {
		String insertRestaurants = "INSERT INTO Restaurants(Name, Description, Menu, Hours, Active, CuisineType, Street1, Street2, City, State, Zip, CompanyName) "
								+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRestaurants);
			insertStmt.setString(1, restaurant.getName());
            insertStmt.setString(2, restaurant.getDescription());
            insertStmt.setString(3, restaurant.getMenu());
            insertStmt.setString(4, restaurant.getHours());
            insertStmt.setBoolean(5,restaurant.isActive());
            insertStmt.setString(6, restaurant.getCuisineType().toString());
            insertStmt.setString(7, restaurant.getStreet1());
            insertStmt.setString(8, restaurant.getStreet2());
            insertStmt.setString(9, restaurant.getCity());
            insertStmt.setString(10, restaurant.getState());
            insertStmt.setInt(11, restaurant.getZip());
            insertStmt.setString(12, restaurant.getCompanyName());
            insertStmt.executeUpdate();
            return restaurant;
		}catch(SQLException e) {
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
	public Restaurants getRestaurantById(int restaurantId) throws SQLException {
        String selectRestaurant = "SELECT RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, Street1, Street2, City, State, Zip, CompanyName "
        						+ "FROM Restaurants "
        						+ "WHERE RestaurantId=?;";
        Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurant);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultRestaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String desc = results.getString("Description");
				String menu = results.getString("Menu");
				String hour = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.CuisineType ct = Restaurants.CuisineType.valueOf(results.getString("CuisineType"));
				String s1 = results.getString("Street1");
				String s2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				String companyName = results.getString("CompanyName");
				Restaurants res = new Restaurants(resultRestaurantId, name, desc, menu, hour, active, ct, s1, s2, city, state, zip, companyName);
				return res;
			}	
		}catch(SQLException e) {
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
	public List<Restaurants> getRestaurantsByCuisine(Restaurants.CuisineType cuisine) throws SQLException{
		List<Restaurants> restaurants = new ArrayList<Restaurants>();
		String selectRestaurants = "SELECT RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, Street1, Street2, City, State, Zip, CompanyName "
								+ "FROM Restaurants "
								+ "WHERE CuisineType=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurants);
			selectStmt.setString(1,cuisine.toString());
			results = selectStmt.executeQuery();
			while(results.next()) {
				int id = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String desc = results.getString("Description");
				String menu = results.getString("Menu");
				String hour = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.CuisineType ct = Restaurants.CuisineType.valueOf(results.getString("CuisineType"));
				String s1 = results.getString("Street1");
				String s2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				String cn = results.getString("CompanyName");
				Restaurants res = new Restaurants(id, name, desc, menu, hour, active, ct, s1, s2, city, state, zip, cn);
				restaurants.add(res);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
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
		return restaurants;
	}
	
	public List<Restaurants> getRestaurantsByCompanyName(String companyName) throws SQLException{
		List<Restaurants> restaurants = new ArrayList<Restaurants>();
		String selectRestaurants = "SELECT RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, Street1, Street2, City, State, Zip, CompanyName FROM Restaurants WHERE CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurants);
			selectStmt.setString(1,companyName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int id = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String desc = results.getString("Description");
				String menu = results.getString("Menu");
				String hour = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.CuisineType ct = Restaurants.CuisineType.valueOf(results.getString("CuisineType"));
				String s1 = results.getString("Street1");
				String s2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				String cn = results.getString("CompanyName");
				Restaurants res = new Restaurants(id, name, desc, menu, hour, active, ct, s1, s2, city, state, zip, cn);
				restaurants.add(res);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
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
		return restaurants;
	}
	public Restaurants delete(Restaurants restaurant)throws SQLException {
		String deleteRestaurant = "DELETE FROM Restaurants WHERE RestaurantId=?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement deleteStmt = connection.prepareStatement(deleteRestaurant)) {
            deleteStmt.setInt(1, restaurant.getRestaurantId());
            deleteStmt.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
	}
}
