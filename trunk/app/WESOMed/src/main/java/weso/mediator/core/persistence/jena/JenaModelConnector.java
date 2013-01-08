package weso.mediator.core.persistence.jena;

import weso.mediator.core.persistence.Connector;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

public class JenaModelConnector implements Connector {
	
	private Model model;

	@Override
	public ResultSet executeQuery(String query) {
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet rs = qexec.execSelect();
		return rs;
	}

	public JenaModelConnector(){
		/*this.model = ModelFactory.createDefaultModel();
		this.model.read(new FileInputStream(
				new File(Configuration.getProperty("datasource_uri"))), "");*/
		this.model = JenaModelFileWrapper.getInstance().getModel();
	}
}
