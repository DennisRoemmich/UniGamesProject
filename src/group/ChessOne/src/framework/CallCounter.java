package framework;

import org.json.simple.JSONObject;

public class CallCounter {

    private static JSONObject callCounts = new JSONObject();

    public static void registerCall(String name, boolean print) {
        if(callCounts.containsKey(name)) {
            int amount = Integer.valueOf(callCounts.get(name).toString());
            callCounts.put(name, String.valueOf(++amount));
            if(print) {
                PrintToConsole.println("call #" + amount + " of "+ name + " @time " + TimeKeeper.timeToString());
            }
        } else {
            callCounts.put(name, "1");
        }
    }

}
