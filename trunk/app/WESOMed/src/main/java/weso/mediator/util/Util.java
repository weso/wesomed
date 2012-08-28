package weso.mediator.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;

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
	
	public static String filterStopWords(String label, List<String> stopWords) throws IOException {
		Set<Object> stopSet = StopFilter.makeStopSet(stopWords,true);
		TokenStream stream = new StandardTokenizer(Version.LUCENE_36, new StringReader(label));
		stream = new LowerCaseFilter(stream);
		stream = new StopFilter(Version.LUCENE_36, stream, stopSet);
		TermAttribute atttribute = stream.getAttribute(TermAttribute.class);
		String result = "";
		while(stream.incrementToken()) {
				result += atttribute.term() + " ";
		}
		result.substring(0, (result.length() > 0)?result.length() - 1:0);
		return result;
	}
	
	
	


	

}
