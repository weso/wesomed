package weso.mediator.core.business;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;

import weso.mediator.config.Configuration;
import weso.mediator.core.domain.Directory;
import weso.mediator.core.domain.Suggestion;
import weso.mediator.core.domain.lucene.IndexLucene;
import weso.mediator.util.Util;

/**
 * This class defines some attributes and implements some methods of the interface SuggestionEngine
 *
 * @param <T> It is the class that implements weso.mediator.domain.Directory
 */
@SuppressWarnings("deprecation")
public abstract class AbstractSuggestionEngine<T extends Directory<?>> implements SuggestionEngine<IndexLucene> {
	
	/**
	 * Its a list of directories where entities are indexed
	 */
	protected List<T> indexDirectories;
	private List<String> stopWords;

	public AbstractSuggestionEngine() throws IOException {
		this(new LinkedList<T>());
	}
	
	public AbstractSuggestionEngine(List<T> directories) throws IOException {
		stopWords = Util.readWords(Configuration.getProperty("stop_words_file"));
		indexDirectories = directories;
	}

	@Override
	public abstract List<Suggestion> getSuggestions(String label,
			String directoryName) throws SuggestionException;

	@Override
	public abstract void indexEntities(String directoryName, String query,
			List<IndexLucene> indexers) throws SuggestionException ;
	
	/**
	 * This method returns the directory from his name.
	 * @param directoryName The name of the directory that the method has to return. 
	 * @return A directory
	 * @throws SuggestionException
	 */
	protected T getDirectory(String directoryName) throws SuggestionException {
		for(T directory : indexDirectories) {
			if(directory.getDirectoryName().equals(directoryName)) {
				return directory;
			}
		}
		throw new SuggestionException("Directory not found");
	}
	
	/**
	 * Removes all stop words that are included 
	 * @param label The label to filter with stop words
	 * @return the filtered label
	 */
	protected String filterStopWords(String label) throws IOException {
		return Util.filterStopWords(label, stopWords);
	}

}
