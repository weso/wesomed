package weso.mediator.core.business;

import java.util.List;

import weso.mediator.core.domain.Index;
import weso.mediator.core.domain.Suggestion;
import weso.mediator.core.domain.SuggestionWithLabel;

/**
 * This interface defines the main operations of the mediator.
 *
 * @param <T>
 */
public interface SuggestionEngine<T extends Index> {
	
	/**
	 * This method returns the suggestions from the label that receives as parameter.
	 * @param label The label that the Mediator have to find.
	 * @param nameDirectory The directory in which the mediator have to search all suggestions from the label. 
	 * @return A list of all the suggestions found by the mediator
	 * @throws SuggestionException
	 */
	public List<Suggestion> getSuggestions(String label, String directoryName) throws SuggestionException;
	
	/**
	 * This method returns the suggestions from the label that receives as parameter. In this method, the
	 * suggestion includes the text.
	 * @param label The label that the Mediator have to find.
	 * @param nameDirectory The directory in which the mediator have to search all suggestions from the label. 
	 * @return A list of all the suggestions found by the mediator
	 * @throws SuggestionException
	 */
	public List<SuggestionWithLabel> getSuggestionsWithLabel(String label, 
			String directoryName) throws SuggestionException;

	/**
	 * This method has to index entities in a directory.
	 * @param directoryName The name of the directory in witch the mediator has to index the entities. 
	 * @param query The query whose results are the entities to index
	 * @param indexers A list of all index that are necessary to search the entities.
	 * @throws SuggestionException
	 */
	public void indexEntities(String directoryName, String query, List<T> indexers) throws SuggestionException;

	/**
	 * This method checks if the indexes are created
	 * @return boolean Indicates if the indexes are created
	 */
	public boolean areIndexesCreated();
	
	
}
