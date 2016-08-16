package evra;

import org.json.*;
import java.util.*;
import java.io.*;

public class EObject {
    public String id;
    private JSONObject data;

    public EObject() {
        id = "";
        data = new JSONObject();
    }

    public EObject(EObject s) {
        id = "";
        data = new JSONObject();
    }

    public EObject(JSONObject o) {
        data = o;
        id = data.optString("id");
        if(id == null) 
            throw new RuntimeException("EObject " + data.optString("name") + " passed JSON without an 'id' tag.");
    }

    public JSONObject getData() {
        return data;
    }

    public void write() {
        Iterator<String> iter = data.keys();
        String s = "";
        String write = "";
        while(iter.hasNext()) {
            s = iter.next();
            if(s.equals("text") || s.equals("description")) {
                if(data.opt(s) instanceof JSONArray) {
                    JSONArray ja = new JSONArray(data.opt(s));
                    Iterator<Object> subIter = ja.iterator();
                    Log.write(s + " ");
                    while(subIter.hasNext()) {
                        Log.writel(subIter.next().toString());
                    }
                }
            }
            Log.writel(s + ": " + data.opt(s).toString());
        }
    }
}