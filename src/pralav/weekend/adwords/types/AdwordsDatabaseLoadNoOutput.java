package pralav.weekend.adwords.types;

import java.io.File;
import java.io.IOException;

import pralav.weekend.adwords.core.AdwordsCore2;
import pralav.weekend.configuration.AdwordsConfig;
import pralav.weekend.database.DB;
import pralav.weekend.utils.FilePathUtils;
import pralav.weekend.utils.FileUtilsPralav;

public class AdwordsDatabaseLoadNoOutput {
    public static void runMe(AdwordsConfig config, int workTokenSize) throws IOException {
        String inputFolder = FilePathUtils.getFolderPath(config.getDataFolderPath());

        File[] listOfFiles = FileUtilsPralav.getListOfFiles(inputFolder);
        // createDataLoadTables(listOfFiles);

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                AdwordsCore2.populateDBFromInputFile(inputFolder, fileName, workTokenSize, config);
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    private static void createDataLoadTables(File[] listOfFiles) {
        for (File file : listOfFiles) {
            DB.createDataLoadTable(file.getName());
        }

    }
}
