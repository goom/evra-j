package evra.database;

import org.json.*;
import java.io.*;
import java.util.*;
import evra.*;

public class JImport {
    public static JSONObject load(String p) {
        try {
            String data = "";
            InputStream is = JImport.class.getResourceAsStream(p);
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }

            data = result.toString("UTF-8");

            is.close();
            return new JSONObject(data);
            //BufferedWriter bw = new BufferedWriter(new FileWriter("import.json"));
            //bw.write(jo.toString(5));
            //bw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject arrayToObject(String s) {
        JSONArray ja = load("/import.json").getJSONArray(s);
        if(ja == null) 
            throw new RuntimeException("arrayToObject failed, array not found");
        JSONObject jo = new JSONObject();
        Iterator i = ja.iterator();
        int x = 0;
        while(i.hasNext()) {
            jo.put(Integer.toString(x++), i.next());
        }

        Log.writel("Converted " + x + " items to objects.");
        return jo;
    }

    public static void save(String name, JSONObject jo) {
        //Writes the current JSON to a spells.json file
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(name + ".json"));
            bw.write(jo.toString(5));
            bw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static String byteString(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;

        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");

        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}