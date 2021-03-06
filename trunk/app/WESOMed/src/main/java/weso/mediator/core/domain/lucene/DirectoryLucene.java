package weso.mediator.core.domain.lucene;

import java.util.List;

import weso.mediator.core.domain.Directory;

/**
 * This class its an implementation of class weso.mediator.domain.Directory using FSDirectory of Lucene
 *
 */
public class DirectoryLucene extends Directory<IndexLucene> {

	public DirectoryLucene(String directoryName, String directoryPath, 
			IndexLucene returnIndex, List<IndexLucene> indexers) {
		super(directoryName, directoryPath, returnIndex, indexers);
	}

	@Override
	public void setIndexers(List<IndexLucene> indexers) {
		this.indexers = indexers;		
	}

	@Override
	public int compareTo(Directory<IndexLucene> o) {
		return this.directoryName.compareTo(o.getDirectoryName());
	}
}
