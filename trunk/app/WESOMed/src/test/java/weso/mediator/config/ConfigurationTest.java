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

}

