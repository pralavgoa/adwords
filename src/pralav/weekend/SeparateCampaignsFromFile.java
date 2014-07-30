package pralav.weekend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.common.base.Strings;

import au.com.bytecode.opencsv.CSVReader;

public class SeparateCampaignsFromFile {

	private static final String DEFAULT_INPUT_FOLDER = "data/input/7_29_2014/input_3.csv";
	private static final String DEFAULT_TEMP_INPUT_FOLDER = "data/input/temp/";

	public static void main(String[] args) throws IOException{

		separateCampaignsToMultipleFiles(DEFAULT_INPUT_FOLDER);
	}

	private static void separateCampaignsToMultipleFiles(String inputFile) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(inputFile));
		String[] nextLine;
		int lineNumber = 0;
		while ((nextLine = reader.readNext()) != null) {
            lineNumber++;
            if ((lineNumber <= 2)) {
                System.out.println("Skipping line number " + lineNumber);
                continue;
            }

			String campaignName = nextLine[3];
			
			if(Strings.isNullOrEmpty(campaignName)){
				continue;
			}

			File file = new File(DEFAULT_TEMP_INPUT_FOLDER+campaignName);
			System.out.println("Create new file "+DEFAULT_TEMP_INPUT_FOLDER+campaignName);
			if(!file.exists()){
				file.createNewFile();
			}
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(DEFAULT_TEMP_INPUT_FOLDER+campaignName, true)))) {
				out.println(campaignName);
			}catch (IOException e) {
				e.printStackTrace();	
			}
		}

	}
}
