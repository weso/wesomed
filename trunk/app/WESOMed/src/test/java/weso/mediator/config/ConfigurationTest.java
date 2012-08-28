package weso.mediator.config;

import static org.junit.Assert.*;

import java.io.File;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void testStopWordsFileName() {
		String fileName = Configuration.getProperty("stop_words_file");
		assertEquals("es/stop_words.txt",fileName);
	}

	@Test
	public void testQueryFileName() {
		String fileName = Configuration.getProperty("query_file");
		assertEquals("sparql/bcn.sparql",fileName);
	}

	@Test
	public void testQueryFileContents() {
		try {
			String contents = Configuration.getContentsFromProperty("query_file");
			assertTrue(contents.contains("SELECT"));
		} catch (Exception e) {
			fail("Exception: e");
		}
		
	}

}

