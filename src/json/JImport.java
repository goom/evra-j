package evra.json;

import org.json.*;
import java.io.*;
import java.util.*;
import evra.*;

public class JImport {
    static String data;
    static JSONObject jo;

    public static void init() {
        try {
            data = "";
            InputStream is = JImport.class.getResourceAsStream("/import.json");
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }

            data = result.toString("UTF-8");
            jo = new JSONObject(data);
            //int s = data.getBytes().length;
            //Log.write("Size of data is: " + byteString(s, true));
            //jo = XML.toJSONObject(result.toString("UTF-8"));

            //delete/unload other stuff
            data = "";
            is = null;
            result = null;
            length = 0;
            buffer = null;


            //BufferedWriter bw = new BufferedWriter(new FileWriter("import.json"));
            //bw.write(jo.toString(5));
            //bw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSpells() {
        JSONArray jar = jo.getJSONArray("spell");
        Iterator<Object> iter = jar.iterator();
        Log.write("Loading spells...");
        for(;iter.hasNext();) {
            JSONObject ob = (JSONObject) iter.next();
            Spell sp = new Spell();
            String s = "";
            int x = 0;
            if(ob.has("name")) sp.name = ob.getString("name");
            if(ob.has("classes")) sp.classes = ob.getString("classes");
            if(ob.has("range")) sp.range = ob.getString("range");
            if(ob.has("components")) sp.components = ob.getString("components");
            if(ob.has("school")) sp.school = ob.getString("school");
            if(ob.has("time")) sp.time = ob.getString("time");
            if(ob.has("duration")) sp.duration = ob.getString("duration");
            if(ob.has("level")) sp.level = ob.getInt("level");
            if(ob.has("text")) {
                JSONArray ar = ob.optJSONArray("text");
                s = "";
                if(ar != null) {
                    Iterator<Object> siter = ar.iterator();
                    while(siter.hasNext()) s += (String) siter.next();
                    sp.description = s;
                }
                else
                    sp.description = ob.getString("text");
            }
            SpellList.add(new Spell(sp));
        }
        Log.writel(SpellList.size() + " spells loaded.");
    }

    public static String byteString(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;

        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");

        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}