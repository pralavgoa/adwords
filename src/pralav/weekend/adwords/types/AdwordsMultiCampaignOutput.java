package pralav.weekend.adwords.types;

import java.io.File;
import java.io.IOException;

import pralav.weekend.AdwordsConstants;
import pralav.weekend.adwords.core.AdwordsCore;
import pralav.weekend.configuration.AdwordsConfig;
import pralav.weekend.utils.FilePathUtils;
import pralav.weekend.utils.FileUtilsPralav;

public class AdwordsMultiCampaignOutput {

    public static void runMe(AdwordsConfig config, String outputFolderName, int workTokenSize) throws IOException{
    	
    	String tempFolder = FilePathUtils.getFolderPath(config.getDataFolderPath(),AdwordsConstants.DEFAULT_TEMP_SUB_FOLDER);
    	String outputFolder = FilePathUtils.getFolderPath(config.getDataFolderPath(),AdwordsConstants.DEFAULT_OUTPUT_FOLDER,outputFolderName);
    	
    	FileUtilsPralav.cleanDirectory(outputFolder);
    	
        File[] listOfFiles = getListOfFiles(tempFolder);
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                AdwordsCore.constructOutputFileFromInputFile(tempFolder+ fileName, outputFolder + fileName,
                		workTokenSize, config);
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    public static File[] getListOfFiles(String pathToDirectory) {
        File folder = new File(pathToDirectory);
        return folder.listFiles();
    }
}
