package weso.mediator.util;

import java.io.File;

public class Util {

	/**
	 * This method delete a directory of the file system
	 * @param f The directory that have to delete
	 * @return A boolean that indicates if the directory was deleted or no
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
