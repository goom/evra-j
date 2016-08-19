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
        //Object writes itself, but it doesn't know what order it should be written in 
        //so it writes in an arbitrary order, thanks to JSON being unordered
        Iterator<String> iter = data.keys();
        String s = "";
        String write = "";
        while(iter.hasNext()) {
            s = iter.next();
            if(data.opt(s) instanceof JSONArray) {
                JSONArray ja = new JSONArray(data.opt(s));
                Iterator<Object> subIter = ja.iterator();
                Log.write(s + " ");
                while(subIter.hasNext()) {
                    Log.writel(subIter.next().toString());
                }
            }
            else
                Log.writel(s + ": " + data.opt(s).toString());
        }
    }
}