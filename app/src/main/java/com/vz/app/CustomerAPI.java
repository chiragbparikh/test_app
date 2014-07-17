package com.vz.app;

/**
 * CustomerAPI interface
 * @author cparikh
 *
 */
public interface CustomerAPI {

	/**
	 * This method inserts a new Customer in database 
	 * @param customerJSON The customerJSON to be persisted 
	 * @return boolean True if successful or else false
	 */
	public boolean createCustomer(String customerJSON);

	/**
	 * This method reads Customer data from the database 
	 * @param custId The customerId to be read from the DB 
	 * @return String The JSON string for the customer data
	 */
	public String getCustomer(String custId);

    /**
     * This method update the Customer using the JSON string provided 
     * @param customerJSON The string containing data to be updated
     * @return boolean True if successful or else False
     */
	public boolean updateCustomer(String customerJSON);

	/**
	 * This method delete the Customer data from the DB for a given CustomerId
	 * @param custId The customerId to be deleted from the DB
	 * @return boolean True if successful or else False
	 */
	public boolean deleteCustomer(String custId);

}
