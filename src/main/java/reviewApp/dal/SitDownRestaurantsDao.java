package reviewApp.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import reviewApp.model.*;

public class SitDownRestaurantsDao extends RestaurantsDao {
	
	private static SitDownRestaurantsDao instance = null;
	protected SitDownRestaurantsDao() {
		super();
	}
	public static SitDownRestaurantsDao getInstance() {
		if(instance == null) {
			instance = new SitDownRestaurantsDao();
		}
		return instance;
	}
	
	public SitDownRestaurants create(SitDownRestaurants sdr) throws SQLException {
		create(new Restaurants(sdr.getName(), sdr.getDescription(),
                sdr.getMenu(), sdr.getHours(), sdr.isActive(),
                sdr.getCuisineType(), sdr.getStreet1(), sdr.getStreet2(),
                sdr.getCity(), sdr.getState(), sdr.getZip(),
                sdr.getCompanyName()));
		
		String insertSitDownRestaurant = "INSERT INTO SitDownRestaurant(RestaurantId, Capacity) VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertSitDownRestaurant);

            // Use the generated RestaurantId from the Restaurants table for the SitDownRestaurant table.
            insertStmt.setInt(1, sdr.getRestaurantId());
            insertStmt.setInt(2, sdr.getCapacity());
            insertStmt.executeUpdate();

            return sdr;
        }catch (SQLException e) {
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
	public SitDownRestaurants getSitDownRestaurantById(int sitDownRestaurantId) throws SQLException {
		String selectSitDownRestaurant = 
				"SELECT Restaurants.RestaurantId AS RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, " +
				"Street1, Street2, City, State, Zip, CompanyName, Capacity " +
				"FROM SitDownRestaurant INNER JOIN Restaurants " +
				"ON SitDownRestaurant.RestaurantId = Restaurants.RestaurantId " +
				"WHERE SitDownRestaurant.RestaurantId=?;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectSitDownRestaurant);
				selectStmt.setInt(1, sitDownRestaurantId);
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
					int capacity = results.getInt("Capacity");
					
					SitDownRestaurants sitDownRestaurant = new SitDownRestaurants(restaurantId, name, description, menu, hours, 
							active, cuisineType, street1, street2, city, state, zip, companyName, capacity);
					return sitDownRestaurant;
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
	public List<SitDownRestaurants> getSitDownRestaurantsByCompanyName(String companyName) throws SQLException {
		List<SitDownRestaurants> sitDownRestaurants = new ArrayList<>();
		String selectSitDownRestaurants =
			"SELECT Restaurants.RestaurantId AS RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, " +
			"Street1, Street2, City, State, Zip, CompanyName, Capacity " +
			"FROM SitDownRestaurant INNER JOIN Restaurants " +
			"ON SitDownRestaurant.RestaurantId = Restaurants.RestaurantId " +
			"WHERE Restaurants.CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSitDownRestaurants);
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
				int capacity = results.getInt("Capacity");

				SitDownRestaurants sitDownRestaurant = new SitDownRestaurants(restaurantId, name, description, menu, hours, 
						active, cuisineType, street1, street2, city, state, zip, companyName, capacity);
				sitDownRestaurants.add(sitDownRestaurant);
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
		return sitDownRestaurants;
	}

	public SitDownRestaurants delete(SitDownRestaurants sitDownRestaurant) throws SQLException {
		String deleteSitDownRestaurant = "DELETE FROM SitDownRestaurant WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteSitDownRestaurant);
			deleteStmt.setInt(1, sitDownRestaurant.getRestaurantId());
			deleteStmt.executeUpdate();

			// Also delete from the superclass (Restaurants) table.
			super.delete(sitDownRestaurant);

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
