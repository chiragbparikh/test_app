package com.vz.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.vz.app.model.Customer;
import com.vz.app.model.Name;
import com.vz.app.util.ResourceFileUtil;

/**
 * @author cparikh
 * 
 */
public class CustomerDAO {

	private Connection conn = null;
	private String url = null;
	private String userName = null;
	private String password = null;
	ResourceFileUtil util = new ResourceFileUtil();
	private PreparedStatement preparedStmt = null;
	private ResultSet rs = null;
	
	
	private static Logger logger = Logger.getLogger("CustomerAPIImpl");


	/**
	 * Constructor
	 */
	public CustomerDAO() {
		if (conn == null) {
			if(openConnection() == true) {
				logger.debug("Connected to the DB successfully");
			} else {
				logger.error("Could not open Database Connection");
			}
		
		}

	}

	
	/**
	 * Open Database Connection
	 * @return boolean Returns whether connection was opened successful
	 */
	private boolean openConnection() {
		boolean connectionSuccessful = true;
		
		url = util.getDatabaseURL();
		userName = util.getUserName();
		password = util.getPassword();


		try {
			if (conn == null) {
				conn = DriverManager.getConnection(url, userName, password);
			}

		} catch (SQLException e) {
			// handle any errors
			logger.error("SQLException: " + e.getMessage());
			logger.error("SQLState: " + e.getSQLState());
			logger.error("VendorError: " + e.getErrorCode());
			e.printStackTrace();
			connectionSuccessful = false;
		}

		return connectionSuccessful;
	}

	
	/**
	 * Close Database Connection
	 * @return boolean Returns whether connection was closed successful
	 */
	public boolean closeConnection() {

		boolean connectionClosed = true;

		try {
			conn.close();

		} catch (SQLException e) {
			logger.error("SQLException: " + e.getMessage());
			logger.error("SQLState: " + e.getSQLState());
			logger.error("VendorError: " + e.getErrorCode());
			e.printStackTrace();
			connectionClosed = false;
		}

		return connectionClosed;

	}
    
	/**
	 * This method inserts a Customer object into the database
	 * @param customer The customer object to be inserted into the database
	 * @return boolean True if the DB insert was successful else False
	 */
	public boolean insertRecord(Customer customer) {

		int rowsUpdated = 0;
		
		String sql = "INSERT INTO Customer(first_name,last_name,email_id,phone_number) "
				+ "VALUES ( ?, ?, ?, ?)";
		
		if(conn == null){
			boolean result = openConnection();
			if(result != true){
				logger.error("Unable to connect to the DB ");
				return false;
			}
		}

		try {

			preparedStmt = conn.prepareStatement(sql);

			preparedStmt.setString(1, customer.getName().getFirst());
			preparedStmt.setString(2, customer.getName().getLast());
			preparedStmt.setString(3, customer.getEmail());
			preparedStmt.setString(4, customer.getPhoneNumber());

			rowsUpdated = preparedStmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQLException: " + e.getMessage());
			logger.error("SQLState: " + e.getSQLState());
			logger.error("VendorError: " + e.getErrorCode());
			e.printStackTrace();
			
		} finally {
			if(preparedStmt != null){
				try {
					preparedStmt.close();
				} catch (SQLException e) {
					logger.error("Error: Unable to close preparedStatement ");
					logger.error("SQLException: " + e.getMessage());
					e.printStackTrace();
				}
			}

		}

		if (rowsUpdated == 1) {
			return true;
		}
		return false;
	}

	/**
	 * This method Reads a Customer records from the database given the customerId
	 * @param custId The Id of the customer object to be retrived from the DB
	 * @return Customer The customer object retrieved from the DB
	 */
	public Customer getRecord(String custId) {
		Customer cust = null;
		
		String sql = "SELECT * from Customer WHERE id=?";

		if(conn == null){
			boolean result = openConnection();
			if(result != true){
				logger.error("Unable to connect to the DB ");
				return cust;
			}
		}
		
		try {

			preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, custId.trim());

			rs = preparedStmt.executeQuery();

			while (rs.next()) {
				cust = new Customer();
				cust.setId(rs.getString("id"));
				Name name = new Name();
				name.setFirst(rs.getString("first_name"));
				name.setLast(rs.getString("last_name"));
				cust.setName(name);
				cust.setEmail(rs.getString("email_id"));
				cust.setPhoneNumber(rs.getString("phone_number"));
			}

		} catch (SQLException e) {
			logger.error("SQLException: " + e.getMessage());
			logger.error("SQLState: " + e.getSQLState());
			logger.error("VendorError: " + e.getErrorCode());
		} finally {
			if(preparedStmt != null){
				try {
					preparedStmt.close();
				} catch (SQLException e) {
					logger.error("Error: Unable to close preparedStatement ");
					logger.error("SQLException: " + e.getMessage());
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("Error: Unable to close ResultSet ");
					logger.error("SQLException: " + e.getMessage());
				}
			}
			
		}

		return cust;
	}

	/**
	 * This method updates the Customer record using the updateCustomer parameter
	 * @param updatedCustomer The Customer object containing updated information
	 * @return boolean True if the Customer data is updated or else False
	 */
	public boolean updateRecord(Customer updatedCustomer) {
		int rowsUpdated = 0;

		String sql = "UPDATE Customer set first_name=?, last_name=?, email_id=?, phone_number=? "
				+ "WHERE id=?";
		
		if(conn == null){
			boolean result = openConnection();
			if(result != true){
				logger.error("Unable to connect to the DB ");
				return false;
			}
		}
		
		try {

			preparedStmt = conn.prepareStatement(sql);

			preparedStmt.setString(1, updatedCustomer.getName().getFirst());
			preparedStmt.setString(2, updatedCustomer.getName().getLast());
			preparedStmt.setString(3, updatedCustomer.getEmail());
			preparedStmt.setString(4, updatedCustomer.getPhoneNumber());
			preparedStmt.setString(5, updatedCustomer.getId());

			rowsUpdated = preparedStmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQLException: " + e.getMessage());
			logger.error("SQLState: " + e.getSQLState());
			logger.error("VendorError: " + e.getErrorCode());
		} finally {
			if(preparedStmt != null){
				try {
					preparedStmt.close();
				} catch (SQLException e) {
					logger.error("Error: Unable to close preparedStatement ");
					logger.error("SQLException: " + e.getMessage());
				}
			}
           
		}

		if (rowsUpdated == 1) {
			return true;
		}
		return false;

	}

	/**
	 * This method deletes a Customer record from the DB given the customerId 
	 * @param custId The customerId of the record to be deleted
	 * @return boolean True if the record is deleted or else False
	 */
	public boolean deleteRecord(String custId) {
		int rowsUpdated = 0;
		
		String sql = "DELETE from Customer WHERE id=?";
		
		if(conn == null){
			boolean result = openConnection();
			if(result != true){
				logger.error("Unable to connect to the DB ");
				return false;
			}
		}
		
		try {
			preparedStmt = conn.prepareStatement(sql);

			preparedStmt.setString(1, custId.trim());

			rowsUpdated = preparedStmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQLException: " + e.getMessage());
			logger.error("SQLState: " + e.getSQLState());
			logger.error("VendorError: " + e.getErrorCode());
		} finally {
			if(preparedStmt != null){
				try {
					preparedStmt.close();
				} catch (SQLException e) {
					logger.error("Error: Unable to close preparedStatement ");
					logger.error("SQLException: " + e.getMessage());
				}
			}

		}

		if (rowsUpdated == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Finalize method to clean up
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		if(conn != null){
			closeConnection();
		}
		if(preparedStmt != null){
			preparedStmt.close();
		}

	}

}
