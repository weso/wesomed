package weso.mediator.core.domain;

/**
 * This class represents a suggestion of the mediator
 *
 */
public class Suggestion implements Comparable<Suggestion>{
	
	/**
	 * Id of the entity suggested
	 */
	private String resourceId;
	/**
	 * Probability that the suggested entity is the correct
	 */
	private float probability;
	
	public Suggestion(String resourceId, float probability) {
		super();
		this.resourceId = resourceId;
		this.probability = probability;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public float getProbability() {
		return probability;
	}
	public void setProbability(float probability) {
		this.probability = probability;
	}
	@Override
	public int compareTo(Suggestion arg0) {
		return (arg0.probability >= this.probability)?1:-1;
	}

}
