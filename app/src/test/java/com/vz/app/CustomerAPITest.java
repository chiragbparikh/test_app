package com.vz.app;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vz.app.util.ResourceFileUtil;

public class CustomerAPITest {

	private ResourceFileUtil util = null; 
	private CustomerAPI custAPI = null;

	@Before
	public void setUp() throws Exception {
        util = new ResourceFileUtil();
        custAPI = new CustomerAPIImpl();
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	/**
	 * Test to Create a new Customer using data in the CustomerSuccess.json file
	 */

	@Test
	public void testCreateCustomerSuccess() {

		// Only JSON input
		// Read the JSON file
		BufferedReader br = null;
		InputStream is = null;
		String customerJSON = "";
		String currentLine = null;
		try {

			is = this.getClass().getResourceAsStream(
					util.getJSONFilePath() + "CustomerSuccess.json");
			br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			while ((currentLine = br.readLine()) != null) {
				sb.append(currentLine);
			}
			customerJSON = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean result = custAPI.createCustomer(customerJSON);

		assertTrue(result);

	}

	/**
	 * Test to Create a new Customer using wrong data in the CustomerFailure.json file
	 */
	@Test
	public void testCreateCustomerFailure() {

		// Only JSON input
		// Read the JSON file
		BufferedReader br = null;
		InputStream is = null;
		String customerJSON = "";
		String currentLine = null;
		try {

			is = this.getClass().getResourceAsStream(
					util.getJSONFilePath() + "CustomerFailure.json");
			br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			while ((currentLine = br.readLine()) != null) {
				sb.append(currentLine);
			}
			customerJSON = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean result = custAPI.createCustomer(customerJSON);

		assertFalse(result);

	}
	
	/**
	 * Test to Create a new Customer using data in the Customer2Success.json file
	 */
	@Test
	public void testCreateCustomer2Success() {

		// Read the JSON file
		BufferedReader br = null;
		InputStream is = null;
		String customerJSON = "";
		String currentLine = null;
		try {

			 is = this.getClass().getResourceAsStream(
					util.getJSONFilePath() + "Customer2Success.json");
			br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			while ((currentLine = br.readLine()) != null) {
				sb.append(currentLine);
			}
			customerJSON = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean result = custAPI.createCustomer(customerJSON);

		assertTrue(result);

	}

	/**
	 * Test to Read the Customer Data for custId = 2
	 */
	@Test
	public void testReadCustomerSuccess() {

		final String customerIdToRead = "2";
		boolean result = false;

		String customerJSON = custAPI.getCustomer(customerIdToRead);

		if (customerJSON != null && !customerJSON.isEmpty()) {
			result = true;
		}

		assertTrue(result);

	}
	
	/**
	 * Test to Create a new Customer using data in the Customer3Success.json file
	 */
	@Test
	public void testCreateCustomer3Success() {

		// Read the JSON file
		BufferedReader br = null;
		InputStream is = null;
		String customerJSON = "";
		String currentLine = null;
		try {

			 is = this.getClass().getResourceAsStream(
					util.getJSONFilePath() + "Customer3Success.json");
			br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			while ((currentLine = br.readLine()) != null) {
				sb.append(currentLine);
			}
			customerJSON = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean result = custAPI.createCustomer(customerJSON);

		assertTrue(result);

	}

	/**
	 * Test to Update Customer data having customerId = 3 using new date in CustomerUpdate.json file
	 */
	@Test
	public void testUpdateCustomerSuccess() {

		// Only JSON input
		// Read the JSON file
		BufferedReader br = null;
		InputStream is = null;
		String customerJSON = "";
		String currentLine = null;
		try {

			is = this.getClass().getResourceAsStream(
					util.getJSONFilePath() + "CustomerUpdate.json");
			br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			while ((currentLine = br.readLine()) != null) {
				sb.append(currentLine);
			}
			customerJSON = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean result = custAPI.updateCustomer(customerJSON);

		assertTrue(result);

	}

	/**
	 * Test to Create a new Customer using data in the Customer4Success.json file
	 */
	@Test
	public void testCreateCustomer4Success() {

		// Read the JSON file
		BufferedReader br = null;
		InputStream is = null;
		String customerJSON = "";
		String currentLine = null;
		try {

			is = this.getClass().getResourceAsStream(
					util.getJSONFilePath() + "Customer4Success.json");
			br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			while ((currentLine = br.readLine()) != null) {
				sb.append(currentLine);
			}
			customerJSON = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (is != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean result = custAPI.createCustomer(customerJSON);

		assertTrue(result);

	}
	

	/**
	 * Test to delete a Customer having customerId = 4
	 */
	@Test
	public void testDeleteCustomerSuccess() {

		final String customerIdToRead = "4";
		boolean result = false;

		result = custAPI.deleteCustomer(customerIdToRead);

		assertTrue(result);

	}
	
	
}
