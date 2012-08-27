package main.java.weso.mediator.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import main.java.weso.mediator.config.Configuration;

import org.apache.commons.io.FileUtils;

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
		  List<String> words = FileUtils.readLines(new File(fileName), "utf-8");
		  return words;
	}

}
