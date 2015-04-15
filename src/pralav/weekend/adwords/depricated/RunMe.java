package pralav.weekend.adwords.depricated;

import java.io.IOException;

import pralav.weekend.adwords.types.AdwordsMultiCampaignOutput;
import pralav.weekend.adwords.types.AdwordsSingleFileOutput;
import pralav.weekend.configuration.AdwordsConfig;

public class RunMe {

    private static final int DEFAULT_WORD_TOKEN_SIZE = 1;

    private static int RUN_MODE = 1;

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

        long startTimeMillis = System.currentTimeMillis();

        if (RUN_MODE == 1) {
            AdwordsSingleFileOutput.runMe(config, "" + startTimeMillis, wordTokenSize);
        } else {
            SeparateCampaignsFromFile.run(config.getDataFolderPath());
            AdwordsMultiCampaignOutput.runMe(config, "" + startTimeMillis, wordTokenSize);
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("Adwords program has finished running. Milliseconds:" + (endTimeMillis - startTimeMillis));
    }

}
