package co.id.telkom.epuskesmas.utils;

public class FileUtils {

    // Get File Extension
    public static String getFileExtension(String fileName) {
        String ext = "";
        String[] arr = fileName.split("\\.");

        if (arr.length > 0) {
            ext = arr[arr.length - 1];
            return "." + ext;
        } else {
            return ext;
        }
    }
}
