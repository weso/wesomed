package weso.mediator.core.persistence;

import com.hp.hpl.jena.query.ResultSet;

/**
 * An interface to define the operations to extract entites to the index
 *
 */
public interface Connector {
	
	public ResultSet executeQuery(String query);

}