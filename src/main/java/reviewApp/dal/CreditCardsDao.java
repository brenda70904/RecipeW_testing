package reviewApp.dal;

import java.sql.*;
import java.sql.Date;
import java.util.*;
//import java.util.ArrayList;
//import java.util.List;
import reviewApp.model.*;

public class CreditCardsDao {
	protected ConnectionManager connectionManager;
	
	private static CreditCardsDao instance = null;
	
	protected CreditCardsDao() {
		connectionManager = new ConnectionManager();
	}
	public static CreditCardsDao getInstance() {
		if(instance == null ) {
			instance = new CreditCardsDao();
		}
		return instance;
	}
	
	public CreditCards create(CreditCards creditCard) throws SQLException{
		String insertCreditCard = "INSERT INTO CreditCards(CardNumber, Expiration, UserName ) "
								+ "VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;		
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCreditCard);
			insertStmt.setLong(1, creditCard.getCardNumber());
			insertStmt.setDate(2, creditCard.getExpiration());
			insertStmt.setString(3, creditCard.getUserName());
			
			insertStmt.executeUpdate();
			return creditCard;
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	public CreditCards getCreditCardByCardNumber(long cardNumber) throws SQLException{
		String selectCardNumber = "SELECT CardNumber, Expiration, UserName "
								+ "FROM CreditCards "
								+ "WHERE CardNumber = ?";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCardNumber);
			selectStmt.setLong(1, cardNumber);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				long resultCreditCard = results.getLong("CardNumber");
				Date expiration = results.getDate("Expiration");
				String userName = results.getString("UserName");
				CreditCards creditCard = new CreditCards(resultCreditCard, expiration, userName);
				return creditCard;
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
	public List<CreditCards> getCreditCardsByUserName(String userName)throws SQLException{
		List<CreditCards> creditCards = new ArrayList<CreditCards>();
		String selectByUsersName = "SELECT CardNumber, Expiration, UserName "
								+ "FROM CreditCards"
								+ " WHERE UserName = ?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;

		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectByUsersName);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				long cardNumber = results.getLong("CardNumber");
				Date expiration = results.getDate("Expiration");
				String resultUserName = results.getString("UserName");
				CreditCards cd = new CreditCards(cardNumber,expiration, resultUserName);
				creditCards.add(cd);
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
		return creditCards;
	}
	
	public CreditCards updateExpiration(CreditCards creditCard, Date newExpiration) throws SQLException {
		String updateExpiration = "UPDATE CreditCards "
								+ "SET Expiration = ? "
								+ "WHERE CardNumber = ?";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateExpiration);
			updateStmt.setDate(1, newExpiration);
			updateStmt.setLong(2, creditCard.getCardNumber());
			updateStmt.executeUpdate();
			
			creditCard.setExpiration(newExpiration);
			return creditCard;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	public CreditCards delete(CreditCards creditCard) throws SQLException{
		String deleteCreditCard = "DELETE FROM CreditCards WHERE cardNumber = ?";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCreditCard);
			deleteStmt.setLong(1, creditCard.getCardNumber());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
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
