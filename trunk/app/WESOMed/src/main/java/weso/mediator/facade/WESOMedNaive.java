package weso.mediator.facade;

import java.util.List;

import weso.mediator.config.Configuration;
import weso.mediator.core.business.SuggestionEngine;
import weso.mediator.core.business.SuggestionException;
import weso.mediator.core.domain.Suggestion;
import weso.mediator.core.domain.SuggestionWithLabel;
import weso.mediator.core.domain.lucene.IndexLucene;
import weso.mediator.core.domain.naive.IndexNaive;
import weso.mediator.factory.SuggestionEngineFactory;

public class WESOMedNaive implements WESOMed<IndexNaive> {
	
	private SuggestionEngineFactory<IndexNaive> factory;
	
	public WESOMedNaive(){
		factory = new SuggestionEngineFactory<IndexNaive> (Configuration.getProperty("business.class.name"));
	}
	
	@Override
	public List<Suggestion> 
		getSuggestions(String label, 
					   String directoryName) 
							   throws SuggestionException {
		SuggestionEngine<IndexNaive> suggestionEngine = (SuggestionEngine<IndexNaive>) factory.getSuggestionEngine();
		return suggestionEngine.getSuggestions(label, directoryName);
	}
	
	@Override
	public List<SuggestionWithLabel> getSuggestionsWithLabel(String label, String directoryName) throws SuggestionException {
		SuggestionEngine<IndexNaive> suggestion = (SuggestionEngine<IndexNaive>) factory.getSuggestionEngine();
		return suggestion.getSuggestionsWithLabel(label, directoryName);
	}

	@Override
	public void indexEntities(String nameDirectory, String query, List<IndexNaive> indexers) throws SuggestionException {
		SuggestionEngine<IndexNaive> suggestion = factory.getSuggestionEngine();
		suggestion.indexEntities(nameDirectory, query, indexers);
		
	}

	@Override
	public boolean areIndexesCreated() {
		SuggestionEngine<IndexNaive> suggestion = factory.getSuggestionEngine();
		return suggestion.areIndexesCreated();
	}
}
