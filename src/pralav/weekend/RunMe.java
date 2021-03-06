package pralav.weekend;

import java.io.IOException;

import pralav.weekend.adwords.types.AdwordsDatabaseLoadNoOutput;
import pralav.weekend.configuration.AdwordsConfig;

public class RunMe {

    private static final int DEFAULT_WORD_TOKEN_SIZE = 1;

    // Read all csv files in a specific folder, process them, and add them to
    // database
    public static void main(String[] args) throws IOException {
        int wordTokenSize = DEFAULT_WORD_TOKEN_SIZE;
        if (args.length < 1) {
            throw new IllegalArgumentException("Please provide the folder path");
        }
        if (args.length == 2) {
            wordTokenSize = Integer.parseInt(args[1]);
        }
        System.out.println("The word token size is set to " + wordTokenSize);
        String dataAndConfigFolderPath = args[0];
        AdwordsConfig config = new AdwordsConfig(dataAndConfigFolderPath);
        System.out.println("Using data folder path: " + config.getDataFolderPath());

        long startTimeMillis = System.currentTimeMillis();

        AdwordsDatabaseLoadNoOutput.runMe(config, wordTokenSize);

        long endTimeMillis = System.currentTimeMillis();
        System.out.println("Adwords program has finished running. Milliseconds:" + (endTimeMillis - startTimeMillis));

    }

}
