package com.vz.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ResourceFileUtil for reading resource files  
 * @author cparikh
 *
 */
public class ResourceFileUtil {
	
	private Properties properties = new Properties();
	InputStream input = null;
	
	public ResourceFileUtil(){
		loadProperties();
	}
	
	public void loadProperties()
	{
		try {

			String fileName = "/config.properties";

			input = ResourceFileUtil.class.getResourceAsStream(fileName);

			if(input==null){
				System.out.println("Sorry, unable to find " + fileName);
				return;
			}

			// load a properties file
			properties.load(input);


		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	public String getJSONFilePath() {
		return properties.getProperty("json.file.location");
	}

	public String getDatabaseURL(){
		return properties.getProperty("url");
	}
	
	public String getUserName(){
		return properties.getProperty("username");
	}
	
	public String getPassword(){
		return properties.getProperty("password");
	}
	
	
	

}
