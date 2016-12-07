package evra.actors;

import evra.*;
import org.json.*;
import java.util.*;
import evra.database.*;

public class PlayerClass {
    JSONObject jo;

    public PlayerClass(JSONObject jo) {
        this.jo = jo;
    }

    public PlayerClass() {
        jo = null;
    }

    public String getName() {
        return jo.optString("name");
    }

    public int getHD() {
        return jo.optInt("hd");
    }

    public Object get(String key) {
        return jo.opt(key);
    }

    public JSONObject getFeature(String s) {
        Iterator iter = jo.optJSONArray("autolevel").iterator();
        while(iter.hasNext()) {
            JSONObject j = (JSONObject) iter.next();
            if(j == null) throw new RuntimeException("Null JSON in PlayerClass.getFeature");

            if(j.optString("name").equals(s)) return new JSONObject(j.toString());
        }

        Log.error("No feature " + s + " found in class " + getName());
        return null;
    }

    public ArrayList<JSONObject> getFeaturesOfLevel(int lvl) {
        ArrayList<JSONObject> aj = new ArrayList<JSONObject>();
        Iterator iter = jo.optJSONArray("autolevel").iterator();
        while(iter.hasNext()) {
            JSONObject j = (JSONObject) iter.next();
            if(j == null) throw new RuntimeException("Null JSON in PlayerClass.getFeaturesOfLevel");

            if(j.optInt("level") == lvl) {
                aj.add(new JSONObject(j.toString()));
            }
        }

        if(aj.isEmpty()){
            Log.error("No features of level " + Integer.toString(lvl) + " found in class " + getName());
            return null;
        }
        else 
            return aj;
    }

    public ArrayList<JSONObject> getFeaturesOfArchtype(String s) {
        ArrayList<JSONObject> aj = new ArrayList<JSONObject>();
        Iterator iter = jo.optJSONArray("autolevel").iterator();
        while(iter.hasNext()) {
            JSONObject j = (JSONObject) iter.next();
            if(j == null) throw new RuntimeException("Null JSON in PlayerClass.getFeaturesOfLevel");

            if(j.optString("archtype").equals(s)) {
                aj.add(new JSONObject(j.toString()));
            }
        }

        if(aj.isEmpty()){
            Log.error("No features of archtype " + s + " found in class " + getName());
            return null;
        }
        else 
            return aj;        
    }

    public static ArrayList<PlayerClass> importAllFromJSON() {
        //open the file and return an array with all the classes to EvraMain
        ArrayList<PlayerClass> r = new ArrayList<PlayerClass>();
        JSONObject j = JImport.load("classes.json");
        Iterator<String> jIter = j.keys();
        while(jIter.hasNext()) { //iterate through each class
            r.add(new PlayerClass(j.optJSONObject(jIter.next())));
        }

        return r;
    }
}