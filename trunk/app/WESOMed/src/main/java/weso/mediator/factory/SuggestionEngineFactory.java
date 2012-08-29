package weso.mediator.factory;

import weso.mediator.core.business.SuggestionEngine;
import weso.mediator.core.domain.Index;

public class SuggestionEngineFactory<T extends Index> {
	private SuggestionEngine<T> suggestion;
	private String className;
	
	public SuggestionEngineFactory(String className){
		this.className = className;
	}
	
	@SuppressWarnings("unchecked")
	public SuggestionEngine<T> getSuggestionEngine(){
		if(suggestion == null){
			try {
				Class<?> clazz = Class.forName(className);		
				suggestion = (SuggestionEngine<T>) clazz.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return suggestion;
	}
}
