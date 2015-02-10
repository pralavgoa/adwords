package pralav.weekend.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import pralav.weekend.AdwordsConstants;
import pralav.weekend.utils.FilePathUtils;

public class AdwordsConfig {
    private final String dataFolderPath;
    private final String configFolderPath;

    private final Set<String> stopWords;

    public AdwordsConfig(String rootFolder) throws IOException {
        String rootFolderPath = FilePathUtils.getFolderPath(rootFolder);
        this.dataFolderPath = rootFolderPath + FilePathUtils.FILE_SEPARATOR + "data";
        this.configFolderPath = rootFolderPath + FilePathUtils.FILE_SEPARATOR + "config";

        File dataFolder = new File(this.dataFolderPath);
        if (!dataFolder.exists()) {
            throw new IllegalArgumentException("The data folder provided is invalid '" + this.dataFolderPath + "'");
        }

        File configFolder = new File(this.configFolderPath);
        if (!configFolder.exists()) {
            throw new IllegalArgumentException("The config folder provided is invalid");
        }

        File stopWordsFile = new File(this.configFolderPath + FilePathUtils.FILE_SEPARATOR
                + AdwordsConstants.STOP_WORDS_FILENAME);
        this.stopWords = StopWordsParser.getStopWords(stopWordsFile);

        System.out.println("There are '" + this.stopWords.size() + "' stop words");
    }

    public Set<String> getStopWords() {
        return this.stopWords;
    }

    public String getDataFolderPath() {
        return this.dataFolderPath;
    }

}
