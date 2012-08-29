package weso.mediator.facade;

import java.util.List;

import weso.mediator.config.Configuration;
import weso.mediator.core.business.SuggestionEngine;
import weso.mediator.core.business.SuggestionException;
import weso.mediator.core.domain.Suggestion;
import weso.mediator.core.domain.SuggestionWithLabel;
import weso.mediator.core.domain.lucene.IndexLucene;
import weso.mediator.factory.BusinessFactory;

public class WESOMedLucene implements WESOMed<IndexLucene> {
	
	private BusinessFactory<IndexLucene> factory;
	
	public WESOMedLucene(){
		factory = new BusinessFactory<IndexLucene>(Configuration.getProperty("business.class.name"));
	}
	
	@Override
	public List<Suggestion> getSuggestions(String label, String directoryName) throws SuggestionException{
		SuggestionEngine<IndexLucene> suggestion = (SuggestionEngine<IndexLucene>)factory.getSuggestion();
		return suggestion.getSuggestions(label, directoryName);
	}
	
	@Override
	public List<SuggestionWithLabel> getSuggestionsWithLabel(String label,
			String directoryName) throws SuggestionException {
		SuggestionEngine<IndexLucene> suggestion = (SuggestionEngine<IndexLucene>)factory.getSuggestion();
		return suggestion.getSuggestionsWithLabel(label, directoryName);
	}

	@Override
	public void indexEntities(String nameDirectory, String query, List<IndexLucene> indexers) throws SuggestionException {
		SuggestionEngine<IndexLucene> suggestion = factory.getSuggestion();
		suggestion.indexEntities(nameDirectory, query, indexers);
		
	}

	@Override
	public boolean areIndexesCreated() {
		SuggestionEngine<IndexLucene> suggestion = factory.getSuggestion();
		return suggestion.areIndexesCreated();
	}
}
