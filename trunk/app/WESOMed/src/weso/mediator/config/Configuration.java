package weso.mediator.config;

import java.io.IOException;
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
				properties.load(Configuration.class.getClassLoader().getResourceAsStream(FILE_NAME));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties.getProperty(propertyName);
	}
}
