package evra;

import org.json.*;
import java.util.*;
import java.io.*;

public class EObject {
    public String id;
    public JSONObject data;

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

    public void write() {
        Iterator<String> iter = data.keys();
        String s = "";
        while(iter.hasNext()) {
            s = iter.next();
            Log.writel(s + ": " + data.opt(s).toString());
        }
    }
}