package pralav.weekend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Adwords {

    private static final String DEFAULT_FILE = "7_15_2014/input_3.csv";
    private static final String DEFAULT_INPUT_FOLDER = "data/input/";
    private static final String DEFAULT_TEMP_INPUT_FOLDER = "data/input/temp/";
    private static final String DEFAULT_OUTPUT_FOLDER = "data/output/";

    private static final int DEFAULT_WORD_TOKEN_SIZE = 1;

    public static void main(String[] args) throws IOException {
    	
    	
        String inputFile = DEFAULT_FILE;
        if (args.length > 0) {
            inputFile = args[0];
        }
        String outputFile = DEFAULT_FILE;
        if (args.length > 1) {
            outputFile = args[1];
        }
        constructOutputFileFromInputFile(DEFAULT_INPUT_FOLDER + inputFile, DEFAULT_OUTPUT_FOLDER + outputFile,
                DEFAULT_WORD_TOKEN_SIZE);
    }
    

    private static void constructOutputFileFromInputFile(String inputFile, String outputFile, int wordsTogether) {

        Map<String, SearchTermMetrics> mapOfTerms = new HashMap<>();

        try {

            System.out.println("Reading input file: " + inputFile);

            CSVReader reader = new CSVReader(new FileReader(inputFile));
            String[] nextLine;

            HeaderLineParts headerLineParts = null;

            int lineNumber = 0;
            while ((nextLine = reader.readNext()) != null) {
                lineNumber++;
                if ((lineNumber == 1)) {
                    System.out.println("Skipping line number " + lineNumber);
                    continue;
                }

                if (lineNumber == 2) {
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
                    if (mapOfTerms.keySet().contains(word)) {

                        SearchTermMetrics metrics = mapOfTerms.get(word);
                        int savedClicks = metrics.getClicks();
                        int savedImpressions = metrics.getImpressions();
                        double savedCost = metrics.getCost();
                        int savedConvertedClicks = metrics.getConvertedClicks();

                        metrics.setClicks(lineParts.getClicks() + savedClicks);
                        metrics.setImpressions(lineParts.getImpressions() + savedImpressions);
                        metrics.setCost(lineParts.getCost() + savedCost);
                        metrics.setConvertedClicks(lineParts.getConvertedClicks() + savedConvertedClicks);

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
            printAdwordsMap(mapOfTerms, outputFile);
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void printAdwordsMap(Map<String, SearchTermMetrics> mapOfTerms, String outputFile) {

        System.out.println("Printing map to file: " + outputFile + " \n");

        appendToFile(outputFile, "word,clicks,impressions,cost,convertedClicks");
        for (String key : mapOfTerms.keySet()) {
            SearchTermMetrics metrics = mapOfTerms.get(key);

            appendToFile(outputFile, "\n" + key + "," + metrics.getClicks() + "," + metrics.getImpressions() + ","
                    + metrics.getCost() + "," + metrics.getConvertedClicks() + ",");
        }

    }

    private static void appendToFile(String fileName, String stringToAppend) {
        try {
            File to = new File(fileName);

            Files.append(stringToAppend, to, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
