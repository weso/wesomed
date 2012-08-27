package test.java.weso.mediator.util;

import static org.junit.Assert.*;

import java.util.List;

import main.java.weso.mediator.config.Configuration;
import main.java.weso.mediator.util.Util;

import org.junit.Assert;
import org.junit.Test;

public class UtilTest {

	@Test
	public void testReadStopWords() {
		String fileName = Configuration.getProperty("stop_words_file");
		try { 
		  List <String> words = Util.readWords(fileName);
		  assertTrue(words.contains("de"));
		} catch (Exception e) {
			fail("Exception " + e);
		}
	}

}
