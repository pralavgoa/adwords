package pralav.weekend.adwords.core;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import pralav.weekend.configuration.AdwordsConfig;
import pralav.weekend.database.DB;
import pralav.weekend.utils.FilePrinters;
import pralav.weekend.utils.NgramIterator;
import pralav.weekend.utils.WordUtils;
import au.com.bytecode.opencsv.CSVReader;

public class AdwordsCore {
    public static void constructOutputFileFromInputFile(String inputFile, String outputFile, int wordsTogether,
            AdwordsConfig config) {

        TokensCollection tableOfTokens = new TokensCollection();

        Set<String> wordsToExclude = config.getStopWords();
        try {

            System.out.println("Reading input file: " + inputFile);

            CSVReader reader = new CSVReader(new FileReader(inputFile));
            String[] nextLine;

            HeaderLineParts headerLineParts = null;

            int lineNumber = 0;
            while ((nextLine = reader.readNext()) != null) {
                lineNumber++;

                if ((lineNumber % 10000) == 0) {
                    System.out.print("Line:" + lineNumber + "|");
                }

                if (lineNumber == 1) {
                    System.out.println("Processing header line " + lineNumber);
                    headerLineParts = new HeaderLineParts(nextLine);
                    continue;
                }

                if (headerLineParts == null) {
                    throw new IllegalStateException("Header line was not processed. Quitting program.");
                }

                FileLineParts lineParts = new FileLineParts(headerLineParts, nextLine);

                String campaignName = lineParts.getCampaignName();
                NgramIterator ngramIterator = new NgramIterator(wordsTogether, lineParts.getWordline());

                while (ngramIterator.hasNext()) {
                    String word = ngramIterator.next();

                    // exclude common words, alphabets and numbers
                    boolean isAlphabeltOrSpace = (word.length() <= 1);
                    boolean isNumber = WordUtils.isWordIsNumber(word);
                    boolean isStopWord = wordsToExclude.contains(word);
                    boolean shouldExcludeWord = isAlphabeltOrSpace || isNumber || isStopWord;
                    if (shouldExcludeWord) {
                        // skip word
                        continue;
                    }

                    if (tableOfTokens.contains(word, campaignName)) {
                        SearchTermMetrics metrics = tableOfTokens.get(word, campaignName);
                        SearchTermMetrics.updateSearchTermMetrics(metrics, lineParts);

                    } else {
                        SearchTermMetrics metrics = SearchTermMetrics.createSearchTermMetric(word, lineParts);
                        tableOfTokens.put(word, campaignName, metrics);
                    }
                }
            }
            System.out.println("Map contains " + tableOfTokens.size() + " words");
            System.out.println("Persisting in database");
            try {
                DB.persistAll(tableOfTokens);
            } catch (SQLException e) {
                System.out.println(e);
            }
            // printAdwordsMap(mapOfTerms, outputFile);
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void printAdwordsMap(Map<String, SearchTermMetrics> mapOfTerms, String outputFile) {

        System.out.println("Printing map to file: " + outputFile + " \n");

        FilePrinters.printType2(mapOfTerms, outputFile);

    }

}
