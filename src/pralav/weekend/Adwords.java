package pralav.weekend;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Adwords {


	private static final String DEFAULT_INPUT_FILE = "data/input/7_15_2014/input_4.csv";

	private static final String DEFAULT_OUTPUT_FILE = "data/output/7_15_2014/input_4.csv";

	public static void main(String[] args) {

		String inputFile = DEFAULT_INPUT_FILE;

		if(args.length>0){
			inputFile = args[0];
		}
		
		String outputFile = DEFAULT_OUTPUT_FILE;
		
		if(args.length > 1){
			outputFile = args[1];
		}

		Map<String, SearchTermMetrics> mapOfTerms = new HashMap<>();

		try {
			
			System.out.println("Reading input file: "+inputFile);
			
			List<String> lines = Files.readLines(new File(
					inputFile), Charsets.UTF_8);

			int lineNumber = 0;
			for (String line : lines) {
				lineNumber++;
				if (lineNumber <= 2 || lineNumber > lines.size() - 2) {
					System.out.println("Skipping line number "+lineNumber);
					continue;
				}
				
				System.out.println(lineNumber);

				FileLineParts lineParts = new FileLineParts(line);
				
				String[] words = lineParts.getWordline().split("\\s");

				for (String word : words) {
					if (mapOfTerms.keySet().contains(word)) {

						SearchTermMetrics metrics = mapOfTerms.get(word);
						int savedClicks = metrics.getClicks();
						int savedImpressions = metrics.getImpressions();
						double savedCost = metrics.getCost();
						int savedConvertedClicks = metrics.getConvertedClicks();

						metrics.setClicks(lineParts.getClicks() + savedClicks);
						metrics.setImpressions(lineParts.getImpressions() + savedImpressions);
						metrics.setCost(lineParts.getCost() + savedCost);
						metrics.setConvertedClicks(lineParts.getConvertedClicks()
								+ savedConvertedClicks);

					} else {
						SearchTermMetrics metrics = new SearchTermMetrics();
						metrics.setClicks(lineParts.getClicks());
						metrics.setImpressions(lineParts.getImpressions());
						metrics.setCost(lineParts.getCost());
						metrics.setConvertedClicks(lineParts.getConvertedClicks());

						mapOfTerms.put(word, metrics);
					}
				}
			}
			System.out.println("Map contains " + mapOfTerms.size() + " words");
			printAdwordsMap(mapOfTerms,outputFile);
			System.out.println("done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printAdwordsMap(Map<String, SearchTermMetrics> mapOfTerms, String outputFile){

		System.out.println("Printing map to file: "+outputFile+" \n");
		
		appendToFile(outputFile,"word,clicks,impressions,cost,convertedClicks");
		for(String key: mapOfTerms.keySet()){
			SearchTermMetrics metrics = mapOfTerms.get(key);

			appendToFile(outputFile,"\n"+key+","+metrics.getClicks()+","+
			metrics.getImpressions()+","+metrics.getCost()+","+
					metrics.getConvertedClicks()+",");
		}

	}

	private static void appendToFile(String fileName, String stringToAppend){
		try{
			File to = new File(fileName);

			Files.append(stringToAppend, to, Charsets.UTF_8);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
