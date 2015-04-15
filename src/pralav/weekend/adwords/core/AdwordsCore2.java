package pralav.weekend.adwords.core;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pralav.weekend.configuration.AdwordsConfig;
import pralav.weekend.database.DB;
import pralav.weekend.utils.FilePathUtils;
import pralav.weekend.utils.FileUtilsPralav;
import pralav.weekend.utils.NgramIterator;
import pralav.weekend.utils.WordUtils;

import com.opencsv.CSVReader;

public class AdwordsCore2 {
    public static void populateDBFromInputFile(String inputFolder, String inputFile, int wordsTogether,
            AdwordsConfig config) {

        Set<String> wordsToExclude = config.getStopWords();
        try {

            String inputFileFullPath = FilePathUtils.getFilePath(inputFolder, inputFile);

            String logFilePath = FilePathUtils.getFilePath(inputFolder, "log", "run.log");

            System.out.println("Reading input file: " + inputFileFullPath);

            com.opencsv.CSVReader reader = new CSVReader(new FileReader(inputFileFullPath));
            String[] nextLine;

            HeaderLineParts headerLineParts = null;

            List<SearchTermMetrics> termList = new ArrayList<>();
            int termCount = 0;
            int errorLineCount = 0;
            int lineNumber = 0;

            while ((nextLine = reader.readNext()) != null) {

                long startTimeMillis = System.currentTimeMillis();
                lineNumber++;

                if (lineNumber == 1) {
                    System.out.println("Processing header line " + lineNumber);
                    headerLineParts = new HeaderLineParts(nextLine);
                    continue;
                }

                if (headerLineParts == null) {
                    throw new IllegalStateException("Header line was not processed. Quitting program.");
                }

                FileLineParts lineParts;
                try {
                    lineParts = new FileLineParts(inputFile, headerLineParts, nextLine);
                } catch (RuntimeException e) {
                    appendToLogFile("error in line number " + lineNumber, logFilePath);
                    errorLineCount++;
                    continue;
                }

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

                    SearchTermMetrics metrics = SearchTermMetrics.createSearchTermMetric(word, lineParts);
                    termList.add(metrics);
                    termCount++;
                    if (termList.size() == 10000) {
                        persistToDatabase(termList, inputFile, logFilePath);
                        termList.clear();
                    }
                }
                processMetadata(lineNumber);
                long endTimeMillis = System.currentTimeMillis();
                if (endTimeMillis > (startTimeMillis + 5000)) {
                    System.out.println("Slow processing");
                }
            }
            try {
                DB.batchPersist(termList, inputFile);
            } catch (SQLException e) {
                appendToLogFile(e.toString(), logFilePath);
            }
            System.out.println("done with " + termCount + " terms");
            System.out.println("total lines processed: " + lineNumber);
            System.out.println("total error lines: " + errorLineCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void persistToDatabase(List<SearchTermMetrics> termList, String tableName, String logFilePath) {
        try {
            System.out.print("|");
            long startTimeMillis = System.currentTimeMillis();
            DB.batchPersist(termList, tableName);
            long endTimeMillis = System.currentTimeMillis();
            System.out.print(((endTimeMillis - startTimeMillis) / 1000));

        } catch (SQLException e) {
            appendToLogFile(e.toString(), logFilePath);
        }
    }

    private static void appendToLogFile(String line, String fileName) {
        FileUtilsPralav.appendToFile(fileName, line + "\n");
    }

    private static void processMetadata(int lineNumber) {

        if ((lineNumber % 10000) == 0) {
            System.out.print("-");
        }
        if (((lineNumber % 100000) == 0)) {
            System.out.println("=" + lineNumber);
        }
    }
}
