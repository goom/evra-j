package evra.testing;

import evra.math.*;
import evra.*;
import org.json.*;
import evra.database.*;

import java.util.*;
import java.math.*;

public class Test {
    public Test() {
        testingOne();
     }

     private void testingOne() { //INCOMPLETE
         //This will help convert the "Rarity: " tags in the items JSON to key:value
         JSONObject jo = EvraMain.items.getData();
         JSONObject newJo = new JSONObject();
         JSONObject joIter = new JSONObject();
         JSONArray ja = new JSONArray();
         int index = 0;
         String sIter = "";
         String found = "";
         Iterator<String> iter = jo.keys();
         boolean replace = false;
         while(iter.hasNext()) {
             sIter = iter.next();
             joIter = jo.getJSONObject(sIter);
             if(joIter.get("text") instanceof JSONArray){
                ja = joIter.getJSONArray("text");
                Iterator<Object> subIter = ja.iterator();
                Object o = null;
                index = 0;
                while(subIter.hasNext()) {
                    o = subIter.next();
                    if(o.toString().contains("Source: ")) {
                        found = o.toString();
                        found = o.toString().substring(found.indexOf(":") + 2, o.toString().length());
                        replace = true;
                        break;
                    }
                    index++;
                }
             }
             if(replace) {
                joIter.put("source", found);
                joIter.remove("text");
                ja.remove(index);
                joIter.put("text", ja);
             }
             newJo.put(sIter, joIter);
             replace = false;
         }

         JImport.save("newItems", newJo);
     }
}