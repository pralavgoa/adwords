package pralav.weekend.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class StopWordsParser {

	public static Set<String> getStopWords(File stopWordsFile) throws IOException {
		Set<String> stopWords = new HashSet<>();
		List<String> wordsList = Files.readLines(stopWordsFile, Charsets.UTF_8);
		
		for(String word: wordsList){
			stopWords.add(word);
		}
		return stopWords;
	}
	
}
