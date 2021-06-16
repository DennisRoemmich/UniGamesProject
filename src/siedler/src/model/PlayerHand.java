package model;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class PlayerHand {
    JSONObject resources;

    public PlayerHand() {
        resources = new JSONObject();
        for(ResourceType type : ResourceType.values()) {
            resources.put(type.toString(), 0);
        }
    }

    public boolean removeResourceSet(JSONObject set){
        for(ResourceType type : ResourceType.values()) {
            if(getAvailableAmount(type) < (int)set.get(type.toString())) {
                return false;
            }
        }
        for(ResourceType type : ResourceType.values()) {
            removeResources(type, (int)set.get(type.toString()));
        }
        return true;
    }

    public boolean removeResources(ResourceType type, int amount) {
        if(getAvailableAmount(type) >= amount) {
            addResources(type, amount * -1);
            return true;
        } else {
            return false;
        }
    }

    public void addResources(ResourceType type, int amount) {
        resources.put(type.toString(), getAvailableAmount(type) + amount);
    }

    public int getAvailableAmount(ResourceType type) {
        return (int) resources.get(type.toString());
    }
}
