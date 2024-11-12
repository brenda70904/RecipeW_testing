package reviewApp.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import reviewApp.model.*;

public class ReservationsDao {

	private static ReservationsDao instance = null;
	protected ConnectionManager connectionManager;

	protected ReservationsDao() {
		connectionManager = new ConnectionManager();
	}

	public static ReservationsDao getInstance() {
		if (instance == null) {
			instance = new ReservationsDao();
		}
		return instance;
	}

	public Reservations create(Reservations reservation) throws SQLException {
		String insertReservation = "INSERT INTO Reservations(Start, End, Size, UserName, RestaurantId) VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReservation, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setTimestamp(1, reservation.getStart());
			insertStmt.setTimestamp(2, reservation.getEnd());
			insertStmt.setInt(3, reservation.getSize());
			insertStmt.setString(4, reservation.getUserName());
			insertStmt.setInt(5, reservation.getRestaurantId());
			insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			int reservationId = -1;
			if (resultKey.next()) {
				reservationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}

			reservation.setReservationId(reservationId);
			return reservation;
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

	public Reservations getReservationById(int reservationId) throws SQLException {
		String selectReservation = "SELECT ReservationId, Start, End, Size, UserName, RestaurantId FROM Reservations WHERE ReservationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservation);
			selectStmt.setInt(1, reservationId);
			results = selectStmt.executeQuery();
			if (results.next()) {
				int resultReservationId = results.getInt("ReservationId");
				Timestamp start = results.getTimestamp("Start");
				Timestamp end = results.getTimestamp("End");
				int size = results.getInt("Size");
				String userName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");

				Reservations reservation = new Reservations(resultReservationId, start, end, size, userName, restaurantId);
				return reservation;
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

	public List<Reservations> getReservationsByUserName(String userName) throws SQLException {
		List<Reservations> reservations = new ArrayList<>();
		String selectReservations = "SELECT ReservationId, Start, End, Size, UserName, RestaurantId FROM Reservations WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservations);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int reservationId = results.getInt("ReservationId");
				Timestamp start = results.getTimestamp("Start");
				Timestamp end = results.getTimestamp("End");
				int size = results.getInt("Size");
				int restaurantId = results.getInt("RestaurantId");

				Reservations reservation = new Reservations(reservationId, start, end, size, userName, restaurantId);
				reservations.add(reservation);
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
		return reservations;
	}

	public List<Reservations> getReservationsBySitDownRestaurantId(int sitDownRestaurantId) throws SQLException {
		List<Reservations> reservations = new ArrayList<>();
		String selectReservations = "SELECT ReservationId, Start, End, Size, UserName, RestaurantId FROM Reservations WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservations);
			selectStmt.setInt(1, sitDownRestaurantId);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int reservationId = results.getInt("ReservationId");
				Timestamp start = results.getTimestamp("Start");
				Timestamp end = results.getTimestamp("End");
				int size = results.getInt("Size");
				String userName = results.getString("UserName");

				Reservations reservation = new Reservations(reservationId, start, end, size, userName, sitDownRestaurantId);
				reservations.add(reservation);
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
		return reservations;
	}


	public Reservations delete(Reservations reservation) throws SQLException {
		String deleteReservation = "DELETE FROM Reservations WHERE ReservationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReservation);
			deleteStmt.setInt(1, reservation.getReservationId());
			deleteStmt.executeUpdate();

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
