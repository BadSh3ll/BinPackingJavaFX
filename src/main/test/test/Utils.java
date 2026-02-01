package test;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;

public class Utils {

    public static void WriteData(List<String[]> data, Path path) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            writer.writeAll(data);
        } catch (Exception e) {
            System.err.println("Error while writing data to " + path.toString());
        }
    }
}
