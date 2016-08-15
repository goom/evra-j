package evra.database;

import org.json.*;
import java.io.*;
import java.util.*;
import evra.*;

public class Database {
    private static JSONObject data;
    private static ArrayList<String> query;
    public String name;
    private String queryString;

    public Database(String name) {
        Log.write("Loading " + name + "...");
        this.name = name;
        data = JImport.load("/" + name + ".json");
        Log.writel(data.length() + " objects loaded.");
        query = new ArrayList<String>();
        queryString = "name";
    }

    public Database() {
        //empty
    }

    public void setQueryString(String q) {
        queryString = q;
    }

    public boolean query(String s) {
        if(!EvraMain.initiated) 
            throw new RuntimeException("Database hasn't been initated.");
        Iterator<String> i = data.keys();
        JSONObject jo = new JSONObject();
        String id = "";
        while(i.hasNext()) {
            id = i.next();
            jo = data.getJSONObject(id);
            String ident = jo.optString(queryString);
            if(ident.toLowerCase().contains(s.toLowerCase())) {
                query.add(id);
                Log.writel(id + ": " + ident);
            }
        }
        if(query.isEmpty()) {
            Log.writel("No results.");
            return false;
        }
        else return true;
    }

    public JSONObject followUp(String s) {
        if(!EvraMain.initiated)
            throw new RuntimeException("Database not initiated.");
        if(query.isEmpty())
            throw new RuntimeException(name + " database followUp() ran without a valid query");

        if(!query.contains(s)) Log.error("Query follow-up ID: " + s + " not in query... Attempting to pull object anyways.");
        query = new ArrayList<String>();
        return getFromID(s);
    }

    public void generateID() {
        if(!EvraMain.initiated)
            throw new RuntimeException("Database not initiated.");
        //Generate new IDs for each spell
        int z = 0;
        JSONObject jo = new JSONObject();
        Iterator<String> i = data.keys();
        while(i.hasNext()) {
            jo.put(Integer.toString(z++), data.getJSONObject(i.next()));
        }
        
        data = jo;
    }

    public JSONObject getFromID(String s) {
        return data.optJSONObject(s);
    }

    public void save() {
        if(!EvraMain.initiated)
            throw new RuntimeException("Database not initiated.");
        //Writes the current JSON to a spells.json file
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(name + ".json"));
            bw.write(data.toString(5));
            bw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}