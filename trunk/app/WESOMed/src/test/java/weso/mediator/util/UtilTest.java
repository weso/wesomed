package weso.mediator.util;

import static org.junit.Assert.*;

import java.util.List;

import weso.mediator.config.Configuration;
import weso.mediator.util.Util;

import org.junit.Assert;
import org.junit.Test;

public class UtilTest {

	@Test
	public void spanishStopWordsContains_la() {
		String fileName = Configuration.getProperty("stop_words_file");
		try { 
		  List <String> words = Util.readWords(fileName);
		  assertTrue(words.contains("la"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception " + e);
		}
	}

	@Test
	public void spanishStopWordsContains_accented_que() {
		String fileName = Configuration.getProperty("stop_words_file");
		try { 
		  List <String> words = Util.readWords(fileName);
		  assertTrue(words.contains("qué"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception " + e);
		}
	}


	@Test
	public void spanishStopWordsDoesntContain_Juan() {
		String fileName = Configuration.getProperty("stop_words_file");
		try { 
		  List <String> words = Util.readWords(fileName);
		  assertFalse(words.contains("Juan"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception " + e);
		}
	}
	
	@Test
	public void testSpanishStopWords() {
		// TODO: This test is not implemented yet
		try {
			String label = "Juan de la Cierva";
			List <String> stop_words = Util.readWords(Configuration.getProperty("stop_words_file"));
			String filtered = Util.filterStopWords(label,stop_words);
			String expected = "juan cierva " ;
			assertEquals(filtered,expected);
		}
		catch (Exception e) {
			 fail("Exception " + e);
		}
	}
	

}
