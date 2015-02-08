package pralav.weekend.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import pralav.weekend.utils.FilePathUtils;

public class AdwordsConfig {

	private final String dataFolderPath;
	private final String configFolderPath;
	
	private final Set<String> stopWords;
	
	public AdwordsConfig(String rootFolder) throws IOException{
		String rootFolderPath = FilePathUtils.getFolderPath(rootFolder);
		this.dataFolderPath = rootFolderPath+FilePathUtils.FILE_SEPARATOR+"data";
		this.configFolderPath = rootFolderPath +FilePathUtils.FILE_SEPARATOR+"config";
		 
		File dataFolder = new File(dataFolderPath);
		if(!dataFolder.exists()){
			throw new IllegalArgumentException("The data folder provided is invalid '"+dataFolderPath+"'");
		}
		
		File configFolder = new File(configFolderPath);
		if(!configFolder.exists()){
			throw new IllegalArgumentException("The config folder provided is invalid");
		}
		
		File stopWordsFile = new File(configFolderPath+FilePathUtils.FILE_SEPARATOR+"stop_words.txt");
		stopWords = StopWordsParser.getStopWords(stopWordsFile);
		
		System.out.println("There are '"+stopWords.size()+"' stop words");
	}
	
	public Set<String> getStopWords(){
		return stopWords;
	}

	public String getDataFolderPath() {
		return dataFolderPath;
	}
	
}
