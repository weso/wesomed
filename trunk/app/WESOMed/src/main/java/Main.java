

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.TermVector;

import weso.mediator.config.Configuration;
import weso.mediator.core.domain.SuggestionWithLabel;
import weso.mediator.core.domain.lucene.IndexLucene;
import weso.mediator.core.persistence.jena.JenaModelFileWrapper;
import weso.mediator.facade.WESOMed;
import weso.mediator.factory.FacadeFactory;

public class Main {

	/**
	 * A simple example of the mediator. In this example, some entities are indexed with its label, and
	 * do a recommendation from the label "Jaume Barrueto"
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			JenaModelFileWrapper.getInstance().loadModelFromFile(new File(Configuration.getProperty("datasource_uri")));
			System.setProperty("http.proxyHost", "proxy.uniovi.es");
			System.setProperty("http.proxyPort", "8888");
			WESOMed<IndexLucene> facade = (WESOMed<IndexLucene>) FacadeFactory.getFacade();
			//Create the indexers that are necessary 
			List<IndexLucene> indexers = new LinkedList<IndexLucene>();
			//This is the index that stores the label of the entities
			indexers.add(new IndexLucene("content", "label", Index.ANALYZED, TermVector.YES, true));
			//This is the index that contains the Id of the entities. It's important to put it in the position 0
			indexers.add(0, new IndexLucene("filename", "resource", Index.NOT_ANALYZED, TermVector.NO, true));

			String query = Configuration.getContentsFromProperty("query_file");
			
			facade.indexEntities(Configuration.getProperty("index_dir"), query, indexers);
			//Request the suggestions
			List<SuggestionWithLabel> suggestions = 
					facade.getSuggestionsWithLabel
							("Barrueto", 
							 Configuration.getProperty("index_dir")
							);
			for(SuggestionWithLabel sug : suggestions) {
				System.out.println(sug.getResourceId() + " " + sug.getProbability() + " " + sug.getLabel());
			}
		
		} catch (Exception e) {
			System.out.println("Exception");
			e.printStackTrace();
		}
	}

}
