package weso.mediator.core.domain.naive;

import org.apache.lucene.document.Field.TermVector;

import weso.mediator.core.domain.Index;

/**
 * An implementation of weso.mediator.domain.Index using Indexers of Lucene to index the entities
 *
 */
public class IndexNaive extends Index {

	private org.apache.lucene.document.Field.Index indexOption; 
	/**
	 * Defines term vector of Lucene Index
	 */
	private TermVector termVectorOption;
	/**
	 * Indicates if the value of the property that contains the Lucene index is extracted 
	 * in the query or the value is passed in the constructor of the Index 
	 */
	private boolean queryable;
	
	public IndexNaive(String fieldName, String property,
			org.apache.lucene.document.Field.Index indexOption,
			TermVector termVectorOption, boolean queryable) {
		super(fieldName, property);
		this.indexOption = indexOption;
		this.termVectorOption = termVectorOption;
		this.queryable = queryable;
	}

	public org.apache.lucene.document.Field.Index getIndexOption() {
		return indexOption;
	}

	public void setIndexOption(org.apache.lucene.document.Field.Index indexOption) {
		this.indexOption = indexOption;
	}

	public TermVector getTermVectorOption() {
		return termVectorOption;
	}

	public void setTermVectorOption(TermVector termVectorOption) {
		this.termVectorOption = termVectorOption;
	}

	public boolean isQueryable() {
		return queryable;
	}

	public void setQueryable(boolean queryable) {
		this.queryable = queryable;
	}	
	
}