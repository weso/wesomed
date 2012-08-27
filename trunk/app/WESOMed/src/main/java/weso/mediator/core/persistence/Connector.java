package weso.mediator.core.persistence;

import com.hp.hpl.jena.query.ResultSet;

/**
 * An interface to define the operations to extract the entites to index
 *
 */
public interface Connector {
	
	public ResultSet executeQuery(String query);

}