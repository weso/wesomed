package weso.mediator.facade;

import java.util.List;

import weso.mediator.core.business.SuggestionException;
import weso.mediator.core.domain.Index;
import weso.mediator.core.domain.Suggestion;
import weso.mediator.core.domain.SuggestionWithLabel;

public interface WESOMed<T extends Index> {
	
	/**
	 * Obtain a list of Suggestions from the label passed as parameter. The search is performed in the
	 * specified directory
	 * @param label to search for
	 * @param directoryName where the search is performed
	 * @return List of objects Suggestion
	 * @throws SuggestionException
	 */
	public List<Suggestion> getSuggestions(String label, String directoryName) throws SuggestionException;
	
	/**
	 * Obtain a list of Suggestions from the label passed as parameter. The search is performed in the
	 * specified directory. The suggestion includes the text.
	 * @param label to search for
	 * @param directoryName where the search is performed
	 * @return List of objects Suggestion
	 * @throws SuggestionException
	 */
	public List<SuggestionWithLabel> getSuggestionsWithLabel(String label, 
			String directoryName) throws SuggestionException;
	
	/**
	 * Creates an index from a query in which we will be able to perform some searches
	 * @param nameDirectory The name we want to give to the directory
	 * @param query The query to make to the database where we have the data in the proper language (SPARQL, SQL, etc.)
	 * @param indexers Properties we want to have indexed
	 * @throws SuggestionException
	 */
	public void indexEntities(String nameDirectory, String query, List<T> indexers) throws SuggestionException;
	
	/**
	 * Checks if there are some indexes created in which we can perform the searches
	 * @return Whether or not the indexes are created
	 */
	public boolean areIndexesCreated();
	
}