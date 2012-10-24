package weso.mediator.core.persistence.sparql;

import weso.mediator.config.Configuration;
import weso.mediator.core.persistence.Connector;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;

/**
 * This class implements the interface Connector and allows the system to execute SPARQL queries
 *
 */
public class SPARQLConnector implements Connector {
	
	/**
	 * This method executes a SPARQL Query in the endpoint specified in the properties file
	 */
	public ResultSet executeQuery(String queryStr) {
		Query query = QueryFactory.create(queryStr, Syntax.syntaxARQ);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				Configuration.getProperty("datasource_uri"),
				query);
		ResultSet rs = qexec.execSelect();
		return rs;
	}
}
