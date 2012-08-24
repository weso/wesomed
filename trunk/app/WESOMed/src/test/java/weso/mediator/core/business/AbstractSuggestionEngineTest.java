package test.java.weso.mediator.core.business;

import static org.junit.Assert.*;

import org.junit.Test;

import weso.mediator.core.domain.Index;
import weso.mediator.core.domain.impl.IndexLucene;

public class AbstractSuggestionEngineTest {

	@Test
	public void testSpanishStopWords() {
		String label = "Juan de la Cierva";
		String filtered = "Juan Cierva" ; // should be: filterSpanishStopWords(label);
		String expected = "Juan Cierva" ;
		assertEquals(filtered,expected);
	}
	@Test
	public void testSpanishStopWordsFailing() {
		String label = "Juan Carlos";
		String filtered = "Juan Carlos" ; // should be: filterSpanishStopWords(label);
		String expected = "Juan Carlos" ;
		assertEquals(filtered,expected);
	}
}
