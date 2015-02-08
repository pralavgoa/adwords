package pralav.weekend.operations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;

import pralav.weekend.AdwordsConstants;
import pralav.weekend.utils.FilePathUtils;
import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Strings;

public class SeparateCampaignsFromFile {

    public static void clean(String tempFolder) throws IOException{
    	File tempDir = new File(tempFolder);
    	if(tempDir.exists()){
    		FileUtils.cleanDirectory(tempDir); 
    	}else{
    		tempDir.mkdir();
    	}
    	
    }
    
    public static void run(String dataFolder) throws IOException {
    	
    	String tempFolder = FilePathUtils.getFolderPath(dataFolder,AdwordsConstants.DEFAULT_TEMP_SUB_FOLDER);
    	
    	clean(tempFolder);
    	
        CSVReader reader = new CSVReader(new FileReader(FilePathUtils.getFilePath(dataFolder,AdwordsConstants.DEFAULT_INPUT_FILE_NAME)));
        String[] nextLine;
        int lineNumber = 0;
        String headerLine = null;
        while ((nextLine = reader.readNext()) != null) {
            lineNumber++;
            if ((lineNumber <= 2) || "Total".equals(nextLine[0])) {
                System.out.println("Skipping line number " + lineNumber);
                if (lineNumber == 2) {
                    headerLine = getLineFromParts(nextLine);
                }
                continue;
            }

            if (headerLine == null) {
                throw new IllegalStateException("check code for header line");
            }

            String campaignName = nextLine[3];

            if (Strings.isNullOrEmpty(campaignName)) {
                continue;
            }

            File file = new File(tempFolder + campaignName);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Create new file " + tempFolder + campaignName);
                writeLineToFile(tempFolder + campaignName, headerLine);
            }
            writeLineToFile(tempFolder + campaignName, getLineFromParts(nextLine));
        }

    }

    public static void writeLineToFile(String fileName, String line) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
            out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLineFromParts(String[] nextLine) {
        StringBuilder line = new StringBuilder();
        String comma = "";
        for (String linePart : nextLine) {
            line.append(comma);
            line.append("\"" + linePart + "\"");
            comma = ",";
        }
        return line.toString();
    }

}
