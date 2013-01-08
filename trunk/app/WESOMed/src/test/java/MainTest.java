import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.TermVector;
import org.junit.Test;

import weso.mediator.config.Configuration;
import weso.mediator.core.domain.SuggestionWithLabel;
import weso.mediator.core.domain.lucene.IndexLucene;
import weso.mediator.core.persistence.jena.JenaModelFileWrapper;
import weso.mediator.facade.WESOMedLucene;
import weso.mediator.factory.WESOMedFactory;

public class MainTest  {

	/**
	 * A running example using the main parts of the mediator. 
	 * In this example, some entities are indexed with its label, and
	 * do a recommendation from the label "Jaume Barrueto"
	 * @param args
	 */
	@Test
	public void testJaumeBarrueto() {
		String searchName = "Jaume Barrueto";
		String expectedSuggestion = "Víctor Barrueto"; 

		try {
			JenaModelFileWrapper.getInstance().loadModelFromFile(new File(Configuration.getProperty("datasource_uri")));
			WESOMedLucene facade = (WESOMedLucene) WESOMedFactory.getWESOMed();
			
			// Create the indexers that are necessary 
			List<IndexLucene> indexers = new LinkedList<IndexLucene>();
			
			// This is the index that stores the label of the entities
			indexers.add(new IndexLucene("content", "label", Index.ANALYZED, TermVector.YES, true));

			// This is the index that contains the Id of the entities. It's important to put it in the position 0
			indexers.add(0, new IndexLucene("filename", "resource", Index.NOT_ANALYZED, TermVector.NO, true));

			String query = Configuration.getContentsFromProperty("query_file");
			
			facade.indexEntities(Configuration.getProperty("index_dir"), query, indexers);

			//Request the suggestions
			List<SuggestionWithLabel> suggestions = 
					facade.getSuggestionsWithLabel(searchName, Configuration.getProperty("index_dir"));
			
			SuggestionWithLabel sug = suggestions.get(0);
			assertEquals(expectedSuggestion,sug.getLabel().trim());
			
		} catch (Exception e) {
			fail("Exception: " + e);
		}
	}

}
