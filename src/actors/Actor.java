package evra.actors;

import evra.actors.Stats;
import evra.Log;
import org.json.*;
import java.util.*;

public class Actor {
    public String name;
    public Stats stats;
    private ArrayList<JSONObject> features;

    public Actor(String n) {
        name = n;
        features = new ArrayList<JSONObject>();
    }

    public void addFeature(JSONObject f) {
        features.add(f);
    }

    public String getName() {
        return name;
    }
}