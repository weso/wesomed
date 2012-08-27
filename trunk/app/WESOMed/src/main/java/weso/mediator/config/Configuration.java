package weso.mediator.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class extract properties of the configuration file
 *
 */
public class Configuration {
	private static Properties properties;
	
	private static final String FILE_NAME = "config.properties";
	
	public static String getProperty(String propertyName){
		if(properties == null){
			properties = new Properties();
			try {
				properties.load(getLocalStream(FILE_NAME));
			} catch (IOException e) {
				// TODO: should print to logger?
				e.printStackTrace();
			}
		}
		return properties.getProperty(propertyName);
	}
	
	public static InputStream getFileFromProperty(String propertyName){
		String fileName = getProperty(propertyName);
	    return getLocalStream(fileName);	
	} 
	
	public static InputStream getLocalStream(String resourceName) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
	}
}
