package framework;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public final class FileController {

    public static final String fileExtension = ".json";

    private FileController() {
        // Prevent initialization
    }

    public static void saveJSON(JSONObject object, String fileName) {
        try (FileWriter file = new FileWriter(fileName + fileExtension)) {
            file.write(object.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject loadJSON(String fileName) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName + fileExtension))
        {
            Object obj = jsonParser.parse(reader);
            JSONObject gameLogJSON = (JSONObject) obj;
            return gameLogJSON;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
