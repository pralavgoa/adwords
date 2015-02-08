package pralav.weekend.adwords.types;

import java.io.IOException;

import pralav.weekend.AdwordsConstants;
import pralav.weekend.adwords.core.AdwordsCore;
import pralav.weekend.configuration.AdwordsConfig;
import pralav.weekend.utils.FilePathUtils;
import pralav.weekend.utils.FileUtilsPralav;

public class AdwordsSingleFileOutput {

	public static void runMe(AdwordsConfig config, String outputFolderName, int workTokenSize) throws IOException{
		String inputFolder = FilePathUtils.getFolderPath(config.getDataFolderPath());
		String outputFolder = FilePathUtils.getFolderPath(config.getDataFolderPath(),AdwordsConstants.DEFAULT_OUTPUT_FOLDER,outputFolderName);
		FileUtilsPralav.cleanDirectory(outputFolder);
		  AdwordsCore.constructOutputFileFromInputFile(inputFolder+ AdwordsConstants.DEFAULT_INPUT_FILE_NAME, outputFolder + AdwordsConstants.DEFAULT_OUTPUT_FILE_NAME,
          		workTokenSize, config);
	}
	
}
