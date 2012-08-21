package weso.mediator.core.domain.impl;

import java.util.List;

import org.apache.lucene.store.RAMDirectory;

import weso.mediator.core.domain.Directory;

/**
 * This class its an implementation of class weso.mediator.domain.Directory using RAMDirectory of Lucene
 *
 */
public class RAMDirectoryLucene extends Directory<IndexLucene> {

	/**
	 * RAMDirectory where entities are indexed
	 */
	private RAMDirectory directory;
	
	public RAMDirectoryLucene(String directoryName, String directoryPath,
			IndexLucene returnIndex, List<IndexLucene> indexers, 
			RAMDirectory directory) {
		super(directoryName, directoryPath, returnIndex, indexers);
		this.directory = directory;
	}
	
	public RAMDirectoryLucene(String directoryName, String directoryPath,
			IndexLucene returnIndex, List<IndexLucene> indexers) {
		super(directoryName, directoryPath, returnIndex, indexers);
	}

	@Override
	public int compareTo(Directory<IndexLucene> o) {
		return this.directoryName.compareTo(o.getDirectoryName());
	}

	@Override
	public void setIndexers(List<IndexLucene> indexers) {
		this.indexers = indexers;
	}
	
	public RAMDirectory getDirectory() {
		return directory;
	}
	
	public void setDirectory(RAMDirectory directory) {
		this.directory = directory;
	}

}
