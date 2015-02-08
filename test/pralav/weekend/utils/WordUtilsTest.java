package pralav.weekend.utils;

import org.junit.Test;

public class WordUtilsTest {

	@Test
	public void testWordUtils(){
		System.out.println(WordUtils.isWordIsNumber(""));	
		System.out.println(WordUtils.isWordIsNumber("-1"));
		System.out.println(WordUtils.isWordIsNumber("Hello"));
		System.out.println(WordUtils.isWordIsNumber("12.2334"));
		System.out.println(WordUtils.isWordIsNumber("-29.23"));
	}
}
