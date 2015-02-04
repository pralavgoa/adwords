package pralav.weekend.configuration;

import java.io.IOException;

import org.junit.Test;

public class AdwordsConfigTest {

	public static final String ROOT_FOLDER = "C:\\_Pralav\\projects\\Adwords\\";
	
	@Test
	public void testAdwordsConfig() throws IOException{
		AdwordsConfig config = new AdwordsConfig(ROOT_FOLDER);
		System.out.println(config.getStopWords());
	}
}
