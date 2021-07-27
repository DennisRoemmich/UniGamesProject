package core;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PositionRepetitionDetector {

    public JSONObject hashes = new JSONObject();

    public int addHash(int hash) {
        if(hashes.containsKey(hash)) {
            int repetitions = Integer.valueOf(hashes.get(hash).toString()) + 1;
            hashes.put(hash, repetitions);
            return repetitions;
        } else {
            hashes.put(hash, 1);
            return 1;
        }
    }

}
