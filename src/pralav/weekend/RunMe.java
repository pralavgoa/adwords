package pralav.weekend;

import java.io.File;
import java.io.IOException;

import pralav.weekend.configuration.AdwordsConfig;

public class RunMe {
	
	private static final int DEFAULT_WORD_TOKEN_SIZE = 1;
	
	public static void main(String[] args) throws IOException{
		
		int wordTokenSize = DEFAULT_WORD_TOKEN_SIZE;
		
		if(args.length<1){
			throw new IllegalArgumentException("Please provide the folder path");
		}
		
		if(args.length ==2){
			wordTokenSize = Integer.parseInt(args[1]);
		}
		
		System.out.println("The word token size is set to "+wordTokenSize);
		
		String dataFolderPath = args[0];

		AdwordsConfig config = new AdwordsConfig(dataFolderPath);
		
		long startTimeMillis = System.currentTimeMillis();
		SeparateCampaignsFromFile.run(config.getDataFolderPath());

		Adwords.runMe(config, ""+startTimeMillis, wordTokenSize);

		long endTimeMillis = System.currentTimeMillis();
		System.out.println("Adwords program has finished running. Milliseconds:"+(endTimeMillis-startTimeMillis));
	}

}
