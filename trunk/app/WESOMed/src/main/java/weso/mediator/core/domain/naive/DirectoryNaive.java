package weso.mediator.core.domain.naive;

import java.util.List;

import weso.mediator.core.domain.Directory;

public class DirectoryNaive extends Directory<IndexNaive> {

	public DirectoryNaive(String directoryName, String directoryPath, 
			IndexNaive returnIndex, List<IndexNaive> indexers) {
		super(directoryName, directoryPath, returnIndex, indexers);
	}

	@Override
	public void setIndexers(List<IndexNaive> indexers) {
		this.indexers = indexers;		
	}

	@Override
	public int compareTo(Directory<IndexNaive> o) {
		return this.directoryName.compareTo(o.getDirectoryName());
	}
}
