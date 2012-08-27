package weso.mediator.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void testStopWordsFileName() {
		String fileName = Configuration.getProperty("stop_wors_file.txt");
		assertEquals(fileName,"es/stop_words.txt");
	}

}
