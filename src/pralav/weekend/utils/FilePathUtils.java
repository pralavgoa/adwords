package pralav.weekend.utils;


public class FilePathUtils {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static final String getFilePath(String... args) {
        StringBuilder path = new StringBuilder();
        for (int i = 0; i < (args.length - 1); i++) {
            path.append(args[i] + FILE_SEPARATOR);
        }
        path.append(args[args.length - 1]);
        return path.toString();
    }

    public static final String getFolderPath(String... args) {
        StringBuilder path = new StringBuilder();
        for (String arg : args) {
            path.append(arg + FILE_SEPARATOR);
        }
        return path.toString();
    }

}
