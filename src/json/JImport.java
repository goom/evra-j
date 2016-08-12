package evra.json;

import org.json.*;
import java.io.*;
import java.util.*;
import evra.Log;

public class JImport {
    String data = "";
    JSONObject jo;

    public JImport() {
        //incomplete
        try {
            InputStream is = this.getClass().getResourceAsStream("/import.xml");
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }

            data = result.toString("UTF-8");
            int s = data.getBytes().length;
            Log.write("Size of data is: " + byteString(s, true));
            jo = XML.toJSONObject(result.toString("UTF-8"));
            //System.out.print(jo.toString(5));

            BufferedWriter bw = new BufferedWriter(new FileWriter("import.json"));
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