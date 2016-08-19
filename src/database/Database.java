package evra.database;

import org.json.*;
import java.io.*;
import java.util.*;
import evra.*;

public class Database {
    private JSONObject data;
    private ArrayList<String> query;
    public String name;
    private String queryString;
    private ArrayList<String> queryOrder;

    public Database(String name) {
        Log.write("Loading " + name + "...");
        this.name = name;
        data = JImport.load("/" + name + ".json");
        Log.writel(data.length() + " objects loaded.");
        query = new ArrayList<String>();
        queryOrder = new ArrayList<String>();
        if(data.has("query")) {
            JSONArray ja = data.getJSONArray("query");
            Iterator<Object> iter = ja.iterator();
            while(iter.hasNext()) {
                queryOrder.add(iter.next().toString());
            }
            data.remove("query");
            queryString = queryOrder.get(0);
        }
        else
            queryString = "name"; //default
    }

    public Database() {
        //empty
    }

    public void writeOrder() {
        for(String a : queryOrder) {
            Log.write(a + ", ");
        }
    }

    public void setQueryString(String q) {
        queryString = q;
    }

    public ArrayList<String> order() {
        return queryOrder;
    }

    public boolean query(String s) {
        if(!EvraMain.initiated) 
            throw new RuntimeException("Database hasn't been initiated.");
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
        return data.getJSONObject(s);
    }

    public JSONObject getData() {
        return data;
    }

    public void write(JSONObject eo) {
        Iterator<String> iter = eo.keys();  
        String s = "";
        String write = "";
        for(String a : queryOrder) {
            if(eo.opt(a) != null) {
                Object o = eo.opt(a);
                if(o instanceof JSONArray) {
                    Log.writel(a.toUpperCase() + ": ");
                    writeArray((JSONArray) o, 1);
                }
                else if(o instanceof JSONObject) {
                    Log.writel(a.toUpperCase() + ": ");
                    writeObject((JSONObject) o, 1);
                }
                else {
                    Log.writel(a.toUpperCase() + ": " + eo.opt(a).toString());
                }
                eo.remove(a);
                iter = eo.keys();
            }
        }

        while(iter.hasNext()) {
            s = iter.next();
            Object o = eo.opt(s);
            if(o instanceof JSONArray) {
                Log.writel(s.toUpperCase() + ": ");
                writeArray((JSONArray) o, 1);
            }
            else if(o instanceof JSONObject) {
                Log.writel(s.toUpperCase() + ": ");
                writeObject((JSONObject) o, 1);
            }
            else {
                Log.writel(s.toUpperCase() + ": " + o.toString());
            }
        }
    }

    private void writeArray(JSONArray ja, int indent) {
        Iterator<Object> subIter = ja.iterator();
        while(subIter.hasNext()) {
            Object o = subIter.next();
            if(o instanceof JSONArray) {
                writeArray((JSONArray) o, indent+1);
            }
            else if(o instanceof JSONObject) {
                writeObject((JSONObject) o, indent+1);
            }
            else {
                for(int i = 0; i < indent; i++)
                    Log.write("-");
                Log.writel(o.toString());
            }
        }
    }

    private void writeObject(JSONObject jo, int indent) {
        Iterator<String> iter = jo.keys();  
        String s = "";
        int i;
        while(iter.hasNext()) {
            s = iter.next();
            Object o = jo.opt(s);
            if(o instanceof JSONArray) {
                for(i = 0; i < indent; i++)
                    Log.write("-");
                Log.writel(s.toUpperCase() + ": ");
                writeArray((JSONArray) o, indent+1);
            }
            else if(o instanceof JSONObject) {
                for(i = 0; i < indent; i++)
                    Log.write("-");
                Log.writel(s.toUpperCase() + ": ");
                writeObject((JSONObject) o, indent+1);
            }
            else {
                for(i = 0; i < indent; i++)
                    Log.write("-");
                Log.writel(s.toUpperCase() + ": " + o.toString());
            }
        }
    }
}