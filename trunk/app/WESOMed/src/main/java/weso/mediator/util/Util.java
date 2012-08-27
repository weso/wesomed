package weso.mediator.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

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
	 * @return a list of the words
	 */
	public static List<String> readWords(String fileName) throws IOException {
//		  List<String> words = FileUtils.readLines(new File(fileName), "utf-8");
//		  return words;
		ResourceBundle words = 
				new PropertyResourceBundle(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
		return new LinkedList<String>(words.keySet());
	}
	


	

}
