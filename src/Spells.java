package evra;

import evra.Log;
import java.util.*;
import java.io.*;
import evra.json.*;
import org.json.*;

public class Spells {
    private static JSONObject spells;

    public static void init() {
        Log.write("Loading spells...");
        spells = JImport.load("/spells.json");
        Log.writel(spells.length() + " spells loaded.");
    }

    public static Spell getFromID(String id) {
        JSONObject jo = spells.optJSONObject(id);
        Spell sp = new Spell();
        if(jo != null) {
            sp.name = jo.optString("name");
            sp.description = jo.optString("description");
            sp.duration = jo.optString("duration");
            sp.time = jo.optString("time");
            sp.school = jo.optString("school");
            sp.components = jo.optString("components");
            sp.range = jo.optString("range");
            sp.classes = jo.optString("classes");
            sp.level = jo.optInt("level");

            return sp;
        }
        else {
            Log.error("No such spell ID: " + id);
            return new Spell();
        }
    }

    public static void generateID() {
        //Generate new IDs for each spell
        int z = 0;
        JSONObject jo = new JSONObject();
        Iterator<String> i = spells.keys();
        while(i.hasNext())
            jo.put(Integer.toString(z++), spells.getJSONObject(i.next()));
        
        spells = jo;
    }

    public static void save() {
        //Writes the current JSON to a spells.json file
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("spells.json"));
            bw.write(spells.toString(5));
            bw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}