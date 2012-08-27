package weso.mediator.core.business;

import static org.junit.Assert.*;

import org.junit.Test;

import weso.mediator.core.domain.Index;
import weso.mediator.core.domain.impl.IndexLucene;
import weso.mediator.core.business.*;

public class AbstractSuggestionEngineTest {

	@Test
	public void testSpanishStopWords() {
		String label = "Juan de la Cierva";
		String filtered = "Juan Cierva" ; // should be: filterSpanishStopWords(label);
		String expected = "Juan Cierva" ;
		assertEquals(filtered,expected);
	}
	
}
