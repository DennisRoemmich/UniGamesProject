package rummikubframework;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public final class FileController {

    public static final String FILEEXTENSION = ".json";

    private FileController() {
        // Prevent initialization
    }

    public static void saveJSon (JSONObject object, String fileName) {
        try (var file = new FileWriter(fileName + FILEEXTENSION)) {
            file.write(object.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject loadJSon(String fileName) {
        var jsonParser = new JSONParser();
        try (var reader = new FileReader(fileName + FILEEXTENSION)) {
            Object obj = jsonParser.parse(reader);
            return (JSONObject) obj;
        } catch (IOException | ParseException e) {
            return null;
        }
    }
}
