package weso.mediator.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import weso.mediator.config.Configuration;

public class Util {

	/**
	 * Deletes a directory of the file system
	 * @param f The directory to delete
	 * @return A boolean indicating if the directory was deleted
	 */
	public static boolean deleteDirectory(File f) {
		if(f.isDirectory()) {
			String[] children = f.list();
			for(String child : children) {
				boolean success = deleteDirectory(new File(f, child));
				if(!success) {
					return false;
				}
			}
		}
		return f.delete();		
	}

	/**
	 * Reads a list of words from a file
	 * @param fileName name of the file
	 * @return list of words
	 */
	public static List<String> readWords(String fileName) throws IOException {
		InputStream input = Configuration.getLocalStream(fileName);
		ResourceBundle words = new PropertyResourceBundle(input);
		return new LinkedList<String>(words.keySet());
	}
	
	
	


	

}
