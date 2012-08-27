package main.java.weso.mediator.core.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a suggestion of the mediator that includes the original label of the entity suggested
 *
 */
public class SuggestionWithLabel extends Suggestion{
	
	/**
	 * A list of possible labels of the entity suggested
	 */
	private List<String> labels;
	
	public SuggestionWithLabel(String resourceId, float probability, List<String> labels) {
		super(resourceId, probability);
		this.labels = labels;
	}
	
	public SuggestionWithLabel(String resourceId, float probability) {
		super(resourceId, probability);
		labels = new LinkedList<String>();
	}	
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public String getLabel() {
		String result = "";
		for(String label : labels) {
			result += label + "/";
		}
		result = result.substring(0, result.lastIndexOf("/"));
		return result;
	}
	public void addLabel(String label) {
		labels.add(label);
	}

}
