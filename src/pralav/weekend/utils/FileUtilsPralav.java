package pralav.weekend.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class FileUtilsPralav {

    public static void makeDir(String path, String dirName) {
        File file = new File(FilePathUtils.getFilePath(path, dirName));
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static void appendToFile(String fileName, String stringToAppend) {
        try {
            File to = new File(fileName);

            Files.append(stringToAppend, to, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanDirectory(String outputFolder) throws IOException {
        File dir = new File(outputFolder);
        if (dir.exists()) {
            FileUtils.cleanDirectory(new File(outputFolder));
        } else {
            dir.mkdirs();
        }
    }

    public static File[] getListOfFiles(String pathToDirectory) {
        File folder = new File(pathToDirectory);
        return folder.listFiles();
    }

    public static List<String> getFilesInFolder(String folderPath) {
        List<String> fileNamesList = new ArrayList<>();
        File[] files = getListOfFiles(folderPath);
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                fileNamesList.add(files[i].getName());
            } else if (files[i].isDirectory()) {
                System.out.println("Directory " + files[i].getName());
            }
        }
        return fileNamesList;
    }
}
