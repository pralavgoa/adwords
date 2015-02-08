package pralav.weekend.adwords.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import pralav.weekend.configuration.AdwordsConfig;
import pralav.weekend.utils.FilePrinters;
import pralav.weekend.utils.FileUtilsPralav;
import pralav.weekend.utils.NgramIterator;
import pralav.weekend.utils.WordUtils;
import au.com.bytecode.opencsv.CSVReader;

public class AdwordsCore {
	public static void constructOutputFileFromInputFile(String inputFile, String outputFile, int wordsTogether,AdwordsConfig config) {

		Map<String, SearchTermMetrics> mapOfTerms = new HashMap<>();
		Set<String> wordsToExclude = config.getStopWords();
		try {

			System.out.println("Reading input file: " + inputFile);

			CSVReader reader = new CSVReader(new FileReader(inputFile));
			String[] nextLine;

			HeaderLineParts headerLineParts = null;

			int lineNumber = 0;
			while ((nextLine = reader.readNext()) != null) {
				lineNumber++;

				if (lineNumber == 1) {
					System.out.println("Processing header line " + lineNumber);
					headerLineParts = new HeaderLineParts(nextLine);
					continue;
				}

				if (headerLineParts == null) {
					throw new IllegalStateException("Header line was not processed. Quitting program.");
				}

				FileLineParts lineParts = new FileLineParts(headerLineParts, nextLine);

				NgramIterator ngramIterator = new NgramIterator(wordsTogether, lineParts.getWordline());

				while (ngramIterator.hasNext()) {
					String word = ngramIterator.next();

					//exclude common words, alphabets and numbers
					boolean isAlphabeltOrSpace = (word.length()<=1);
					boolean isNumber = WordUtils.isWordIsNumber(word);
					boolean isStopWord = wordsToExclude.contains(word);
					boolean shouldExcludeWord = isAlphabeltOrSpace || isNumber || isStopWord;
					if(shouldExcludeWord){
						//skip word
						continue;
					}

					if (mapOfTerms.keySet().contains(word)) {
						SearchTermMetrics metrics = mapOfTerms.get(word);
						SearchTermMetrics.updateSearchTermMetrics(metrics, lineParts);

					} else {
						SearchTermMetrics metrics = SearchTermMetrics.createSearchTermMetric(lineParts);
						mapOfTerms.put(word, metrics);
					}
				}
			}
			System.out.println("Map contains " + mapOfTerms.size() + " words");
			printAdwordsMap(mapOfTerms, outputFile);
			System.out.println("done");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private static void printAdwordsMap(Map<String, SearchTermMetrics> mapOfTerms, String outputFile) {

		System.out.println("Printing map to file: " + outputFile + " \n");

		FilePrinters.printType2(mapOfTerms,outputFile);

	}

}
