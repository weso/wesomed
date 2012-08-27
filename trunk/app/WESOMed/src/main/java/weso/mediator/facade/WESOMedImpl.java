package main.java.weso.mediator.facade;

import java.util.List;

import main.java.weso.mediator.config.Configuration;
import main.java.weso.mediator.core.business.SuggestionEngine;
import main.java.weso.mediator.core.business.SuggestionException;
import main.java.weso.mediator.core.domain.Suggestion;
import main.java.weso.mediator.core.domain.SuggestionWithLabel;
import main.java.weso.mediator.core.domain.impl.IndexLucene;
import main.java.weso.mediator.factory.BusinessFactory;

public class WESOMedImpl implements WESOMed<IndexLucene> {
	
	private BusinessFactory<IndexLucene> factory;
	
	public WESOMedImpl(){
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
