package pralav.weekend;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Adwords {

    private static final String DEFAULT_INPUT_FOLDER = "data/input/temp/";
    private static final String DEFAULT_OUTPUT_FOLDER = "data/output/7_29_2014/";

    private static final int DEFAULT_WORD_TOKEN_SIZE = 1;

    public static void main(String[] args) throws IOException {
        File[] listOfFiles = getListOfFiles(DEFAULT_INPUT_FOLDER);
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                constructOutputFileFromInputFile(DEFAULT_INPUT_FOLDER + fileName, DEFAULT_OUTPUT_FOLDER + fileName,
                        DEFAULT_WORD_TOKEN_SIZE);
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    public static File[] getListOfFiles(String pathToDirectory) {
        File folder = new File(pathToDirectory);
        return folder.listFiles();
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
        boolean hasWordWithNoConvertedClicks = false;
        for (String key : mapOfTerms.keySet()) {
            SearchTermMetrics metrics = mapOfTerms.get(key);
            if(metrics.getConvertedClicks()>0){
            appendToFile(outputFile, "\n" + key + "," + metrics.getClicks() + "," + metrics.getImpressions() + ","
                    + metrics.getCost() + "," + metrics.getConvertedClicks() + ",");
            }else{
            	hasWordWithNoConvertedClicks = true;
            }
        }
        
        if(hasWordWithNoConvertedClicks){
        	appendToFile(outputFile+"_0", "word,clicks,impressions,cost,convertedClicks");
        	for (String key : mapOfTerms.keySet()) {
                SearchTermMetrics metrics = mapOfTerms.get(key);
                if(metrics.getConvertedClicks()==0){
                appendToFile(outputFile+"_0", "\n" + key + "," + metrics.getClicks() + "," + metrics.getImpressions() + ","
                        + metrics.getCost() + "," + metrics.getConvertedClicks() + ",");
                }
            }
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
