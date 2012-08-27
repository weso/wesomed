package main.java.weso.mediator.core.domain;

import java.util.List;

/**
 * This class represents a directory in which mediator index the entities and search them.
 * @param <T> A class that extends of Index.
 */
public abstract class Directory<T extends Index> implements Comparable<Directory<T>>{

	protected String directoryName;
	protected String directoryPath;
	protected List<T> indexers;
	protected T returnIndex;
	

	/**
	 * Construct a directory.
	 * @param directoryName name for the directory
	 * @param directoryPath path of the directory
	 * @param returnIndex name of the field of the directory which value will be return in the suggestions.
	 * @param indexers A list of other indexable fields of the directory 
	 */
	public Directory(String directoryName, String directoryPath, T returnIndex, List<T> indexers) {
		super();
		this.directoryName = directoryName;
		this.directoryPath = directoryPath;
		this.indexers = indexers;
		this.returnIndex = returnIndex;
	}
	
	public T getReturnedIndex() {
		return returnIndex;
	}
	
	public String getDirectoryName() {
		return directoryName;
	}
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}
	public String getDirectoryPath() {
		return directoryPath;
	}
	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}
	public List<T> getIndexers() {
		return indexers;
	}
	public abstract void setIndexers(List<T> indexers) ;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((directoryName == null) ? 0 : directoryName.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Directory){
			return ((Directory<T>) obj).directoryName.equals(directoryName);
		}
		return false;
	}
	
	
	
}
