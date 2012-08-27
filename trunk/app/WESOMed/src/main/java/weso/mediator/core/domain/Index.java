package main.java.weso.mediator.core.domain;

/**
 * This class represents indexes that are used by the mediator to search and index entities in the directories. 
 *
 */
public abstract class Index {
	
	private String fieldName;
	private String property;
	
	/**
	 * Contruct an Index.
	 * @param fieldName The name of the field that you want to index.
	 * @param property The name of the property in the query that contain the value of this index.
	 */
	public Index(String fieldName, String property) {
		this.fieldName = fieldName;
		this.property = property;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}

}