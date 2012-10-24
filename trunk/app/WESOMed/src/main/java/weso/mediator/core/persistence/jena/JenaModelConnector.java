package weso.mediator.core.persistence.jena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import weso.mediator.config.Configuration;
import weso.mediator.core.persistence.Connector;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class JenaModelConnector implements Connector {
	
	private Model model;

	@Override
	public ResultSet executeQuery(String query) {
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet rs = qexec.execSelect();
		return rs;
	}

	public JenaModelConnector() throws FileNotFoundException{
		this.model = ModelFactory.createDefaultModel();
		this.model.read(new FileInputStream(
				new File(Configuration.getProperty("datasource_uri"))), "");
	}
}
