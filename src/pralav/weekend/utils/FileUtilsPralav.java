package pralav.weekend.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class FileUtilsPralav {
    public static void appendToFile(String fileName, String stringToAppend) {
        try {
            File to = new File(fileName+".csv");

            Files.append(stringToAppend, to, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void cleanDirectory(String outputFolder) throws IOException{
    	File dir = new File(outputFolder);
    	if(dir.exists()){
    	FileUtils.cleanDirectory(new File(outputFolder)); 
    	}else{
    		dir.mkdirs();
    	}
    }
}