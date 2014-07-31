package pralav.weekend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Strings;

public class SeparateCampaignsFromFile {

    private static final String DEFAULT_INPUT_FOLDER = "data/input/7_29_2014/input_4.csv";
    private static final String DEFAULT_TEMP_INPUT_FOLDER = "data/input/temp/";

    public static void main(String[] args) throws IOException {

        separateCampaignsToMultipleFiles(DEFAULT_INPUT_FOLDER);
    }

    private static void separateCampaignsToMultipleFiles(String inputFile) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(inputFile));
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

            File file = new File(DEFAULT_TEMP_INPUT_FOLDER + campaignName);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Create new file " + DEFAULT_TEMP_INPUT_FOLDER + campaignName);
                writeLineToFile(DEFAULT_TEMP_INPUT_FOLDER + campaignName, headerLine);
            }
            writeLineToFile(DEFAULT_TEMP_INPUT_FOLDER + campaignName, getLineFromParts(nextLine));
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
