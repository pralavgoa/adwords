package pralav.weekend.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileConversionUtils {

    /**
     * Converts files from UTF 16 LE to UTF 8
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        String directory = "C:\\_Pralav\\personal\\projects\\adwords\\april_data\\data_utf_16\\a\\";
        String outputFolder = "utf8\\";

        File[] listOfFiles = FileUtilsPralav.getListOfFiles(directory);
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                System.out.println("Analyzing " + fileName);
                FileInputStream fstream = new FileInputStream(directory + fileName);
                DataInputStream in = new DataInputStream(fstream);

                try (BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-16LE"))) {
                    long count = 0;
                    StringBuilder fileContentString = new StringBuilder();
                    for (String line; (line = br.readLine()) != null;) {

                        if (count == 0) {
                            System.out.println(line);
                            line = line.substring(1, line.length());
                            System.out.println(line);
                            FileUtilsPralav.appendToFile(directory + outputFolder + fileName, line + "\n");
                            count++;
                            continue;
                        }

                        fileContentString.append(line + "\n");

                        if (((count % 100000) == 0)) {
                            System.out.println(line);
                            System.out.println("line number " + count);
                            FileUtilsPralav.appendToFile(directory + outputFolder + fileName,
                                    fileContentString.toString());
                            fileContentString = new StringBuilder();
                        }

                        count++;
                    }
                    FileUtilsPralav.appendToFile(directory + outputFolder + fileName, fileContentString.toString());
                    System.out.println("Wrote " + count + " lines");
                    // line is not visible here.
                }

            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

    }
}
