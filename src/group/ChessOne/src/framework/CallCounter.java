package framework;

import org.json.simple.JSONObject;

public final class CallCounter {
	
    private static JSONObject callCounts = new JSONObject();
	
	private CallCounter() {
		//Unused
	}

    @SuppressWarnings("unchecked")
	public static void registerCall(String name, boolean print) {
        if (callCounts.containsKey(name)) {
            int amount = Integer.parseInt(callCounts.get(name).toString());
            callCounts.put(name, String.valueOf(++amount));
            if (print) {
                PrintToConsole.println("call #" + amount + " of " + name + " @time " + TimeKeeper.timeToString());
            }
        } else {
            callCounts.put(name, "1");
        }
    }

}
