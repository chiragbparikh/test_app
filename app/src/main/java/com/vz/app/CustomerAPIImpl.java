package com.vz.app;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.vz.app.util.ResourceFileUtil;

import com.vz.app.dao.CustomerDAO;
import com.vz.app.model.Customer;
import com.vz.app.model.Name;

/**
 * CustomerAPIImpl class which implements the CustomerAPI's
 * @author cparikh
 *
 */
public class CustomerAPIImpl implements CustomerAPI {

	CustomerDAO customerDAO = null;
	ResourceFileUtil util = new ResourceFileUtil();
	Customer customer = null;
	Set<ConstraintViolation<Customer>> violations = null;

	private static Logger logger = Logger.getLogger("CustomerAPIImpl");

	public CustomerAPIImpl() {
		if (customerDAO == null) {
			customerDAO = new CustomerDAO();
		}
	}

	/* (non-Javadoc)
	 * @see com.vz.app.CustomerAPI#createCustomer(java.lang.String)
	 */
	public boolean createCustomer(String customerJSON) {

		logger.info("Creating New Customer for ->" + customerJSON);
		boolean retCode = false;

		// Read the JSON file and create the customer object
		ObjectMapper mapper = new ObjectMapper();
		try {
			customer = mapper.readValue(customerJSON, Customer.class);
		} catch (JsonGenerationException e) {
			logger.error("Json Generation Exception: " + e.getMessage());
			e.printStackTrace();

		} catch (JsonMappingException e) {
			logger.error("Json Mapping Exception: " + e.getMessage());
			e.printStackTrace();

		} catch (IOException e) {
			logger.error("IOException: " + e.getMessage());
			e.printStackTrace();

		}
		
		if(customer == null){
			logger.error("The customer JSON could not be read");
			return retCode;
		}

		// Validation
		violations = validateCustomer(customer);

		if (!violations.isEmpty()) {
			Iterator<ConstraintViolation<Customer>> itr = violations.iterator();
			while (itr.hasNext()) {
				logger.error(itr.next().getMessage());
			}
			return retCode;
		}

		// Save the customer Object into DB
		retCode = customerDAO.insertRecord(customer);

		logger.info("The new customer was created [" + retCode + "]");

		return retCode;
	}

	public String getCustomer(String custId) {
		String jsonResult = "";
		
		logger.info("Reading customer data for customerId [" + custId + "]");

		// Read the Data from the DB and re-serialize as JSON
		if (custId == null || custId.trim().equals("")) {
			logger.error("Please input a valid CustomerId!");
			return jsonResult;
		}

		customer = customerDAO.getRecord(custId);

		// If customer is retrived from DB then re-serialize as JSON
		if(customer == null){
			logger.error("Customer Data retrival failed");
			return jsonResult;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonResult = mapper.writeValueAsString(customer);
		} catch (JsonGenerationException e) {
			logger.error("Json Generation Exception: " + e.getMessage());
			e.printStackTrace();

		} catch (JsonMappingException e) {
			logger.error("Json Mapping Exception: " + e.getMessage());
			e.printStackTrace();

		} catch (IOException e) {
			logger.error("IOException: " + e.getMessage());
			e.printStackTrace();
		}
		logger.info("Customer data for custId[" + custId + "] is "+jsonResult);

		return jsonResult;
	}

	public boolean updateCustomer(String customerJSON) {
		// Read the JSON file and create the customer object
		ObjectMapper mapper = new ObjectMapper();
		Customer originalCustomer = null;
		boolean updateSuccessful = false;

		logger.info("Reading Update JSON !");

		try {
			customer = mapper.readValue(customerJSON, Customer.class);
		} catch (JsonGenerationException e) {
			logger.error("Json Generation Exception: " + e.getMessage());
			e.printStackTrace();

		} catch (JsonMappingException e) {
			logger.error("Json Mapping Exception: " + e.getMessage());
			e.printStackTrace();

		} catch (IOException e) {
			logger.error("IOException: " + e.getMessage());
			e.printStackTrace();
		}

		if(customer == null){
			logger.error("The customer update JSON could not be read");
			return updateSuccessful;
		}
		// Validate the input
		violations = validateCustomer(customer);

		if (!violations.isEmpty()) {
			Iterator<ConstraintViolation<Customer>> itr = violations.iterator();
			while (itr.hasNext()) {
				logger.error(itr.next().getMessage());
			}
			return updateSuccessful;
		}

		
		// Query the customer object from the DB
		if (customer.getId() != null && !customer.getId().trim().equals("")) {
			logger.info("CustomerId is [" + customer.getId() + "]");
			originalCustomer = customerDAO.getRecord(customer.getId());
		}

		
		if (originalCustomer != null) {

			// Update the customer object queried from the DB
			Name name = new Name();
			name.setFirst(customer.getName().getFirst());
			name.setLast(customer.getName().getLast());
			originalCustomer.setName(name);
			originalCustomer.setEmail(customer.getEmail());
			originalCustomer.setPhoneNumber(customer.getPhoneNumber());

			updateSuccessful = customerDAO.updateRecord(originalCustomer);
			
		} else {
			logger.error("The Customer getRecord method failed for CustomerId["+customer.getId()+"]");
			updateSuccessful = false;
		}

		return updateSuccessful;
	}

	public boolean deleteCustomer(String custId) {
		boolean deleteSuccessful = false;
		// Delete the data from the DB
		if (custId == null || custId.trim().equals("")) {
			logger.error("Please input a valid CustomerId!");
			return deleteSuccessful;
		}

		deleteSuccessful = customerDAO.deleteRecord(custId);

		return deleteSuccessful;
	}

	public Set<ConstraintViolation<Customer>> validateCustomer(Customer cust) {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Customer>> violations = validator.validate(cust);
		return violations;
	}

}
