package weso.mediator.core.business;

public class SuggestionException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2576776857378291858L;

	public SuggestionException(String msg) {
		super(msg);
	}
	
	public SuggestionException(Exception e) {
		super(e);
	}
}