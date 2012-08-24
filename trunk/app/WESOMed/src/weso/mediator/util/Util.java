package weso.mediator.util;

import java.io.File;

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

}
