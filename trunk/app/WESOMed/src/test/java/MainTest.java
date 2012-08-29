import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.TermVector;
import org.junit.Test;
 

import weso.mediator.config.Configuration;
import weso.mediator.core.domain.SuggestionWithLabel;
import weso.mediator.core.domain.lucene.IndexLucene;
import weso.mediator.facade.WESOMed;
import weso.mediator.facade.WESOMedLucene;
import weso.mediator.factory.FacadeFactory;
import weso.mediator.util.Util;

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
		String expectedSuggestion = "Víctor Barrueto "; 

		try {
			WESOMedLucene facade = (WESOMedLucene) FacadeFactory.getFacade();
			
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
			assertEquals(expectedSuggestion,sug.getLabel());
			
		} catch (Exception e) {
			fail("Exception: " + e);
		}
	}

}