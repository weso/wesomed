package weso.mediator.config;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void testStopWordsFileName() {
		String fileName = Configuration.getProperty("stop_words_file");
		assertEquals("es/stop_words.txt",fileName);
	}

	@Test
	public void testStopWordsFile() {
		String fileName = Configuration.getProperty("stop_words_file");
		File directory = new File (".");
		try {
		System.out.println("Directory: " + directory.getCanonicalPath());
		} catch(Exception e) {
			System.out.println("Exceptione is ="+e.getMessage());
		}
		File file = new File(fileName);
		assertTrue(file.exists());
	}
}

