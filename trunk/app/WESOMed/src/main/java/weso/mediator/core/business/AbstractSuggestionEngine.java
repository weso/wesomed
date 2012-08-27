package main.java.weso.mediator.core.business;

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

import main.java.weso.mediator.config.Configuration;
import main.java.weso.mediator.core.domain.Directory;
import main.java.weso.mediator.core.domain.Suggestion;
import main.java.weso.mediator.core.domain.impl.IndexLucene;

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
	
	/**
	 * Its a collection of spanish stop words that are eliminated from the label that need a suggestion of entities
	 */
	private String[] SPANISH_STOP_WORDS = {
			"de","la","que","el","en","y","a","los","del","se","las","por","un","para",
			"con","no","una","su","al","es","lo","como","más","pero","sus","le","ya","o",
			"fue", "ha","sí","porque","esta","son","entre","cuando","muy","sin",
			"sobre","ser","me","hasta","hay","donde","han","quien", "desde","todo","durante","todos","uno","les","ni","contra",
			"otros","fueron","ese","eso","había","ante","ellos","e","esto","mí","antes","algunos",
			"qué","unos","yo","otro","otras","otra","él","tanto","esa","estos","mucho","quienes",
			"nada"};
	
	public AbstractSuggestionEngine() {
		this.indexDirectories = new LinkedList<T>();
	}
	
	public AbstractSuggestionEngine(List<T> directories) {
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
	 * This method remove from the parameters all stop words thar are include in the attribute SPANISH_STOP_WORDS
	 * @param label The label to filter with stop words
	 * @return The filtered label
	 */
	protected String filterSpanishStopWords(String label) {
		Set<Object> stopWords = StopFilter.makeStopSet(SPANISH_STOP_WORDS, true);
		TokenStream stream = new StandardTokenizer(Version.LUCENE_36, new StringReader(label));
		stream = new LowerCaseFilter(stream);
		stream = new StopFilter(Version.LUCENE_36, stream, stopWords);
		TermAttribute atttribute = stream.getAttribute(TermAttribute.class);
		String result = "";
		try {
			while(stream.incrementToken()) {
				result += atttribute.term() + " ";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		result.substring(0, (result.length() > 0)?result.length() - 1:0);
		return result;
	}

}
