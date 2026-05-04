package se.kth;

import java.nio.file.*;

class Utilities {
    private Utilities() {}

    /**
     * Get the database information from the text file
     * @param fileName the file that contains the data.
     * @return string with the database information
     */
    static String readDatabase(String fileName) {
        Path databasePath = Paths.get(fileName);
        try {
            String data = Files.readString(databasePath);
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * Split the database into line so it is prepared to get the information from them,
     * thus making it is easier to process.
     * @param database the database information.
     * @return string array with representing the information in lines.
     */
    static String[] getInput(String database) {
        String[] data = database.split("\n");
        for(int i = 0; i < data.length; i++) {
            data[i] = data[i].replaceAll("\r", "");
        }
        return data;
    }
}
