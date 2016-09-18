package evra.testing;

import evra.math.*;
import evra.*;
import org.json.*;
import evra.database.*;

import java.util.*;
import java.math.*;

public class Test {
    public Test() {
        TestingTwo();
     }

     private void rarityTags() { //complete
         //This will help convert the "Rarity: " tags in the items JSON to key:value
         JSONObject jo = EvraMain.getDB("items").getData();
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

     public void autolevelFormat() {
         JSONObject j = JImport.load("classes.json");
         Iterator<String> jIter = j.keys();
         while(jIter.hasNext()) { //loop through each class
            JSONArray ja = j.optJSONObject(jIter.next()).optJSONArray("autolevel");
            Iterator jaIter = ja.iterator();
            if(ja == null) throw new RuntimeException("Null JSONArray in autolevelFormat()");
            while(jaIter.hasNext()) { //iterate through each feature of the class
                JSONObject subJ = (JSONObject) jaIter.next();
                if(subJ == null) throw new RuntimeException("Null JSONOBject in autolevelFormat");

                if(subJ.has("slots")) { //format spell slots

                }
                else { //format 'features'
                    if(subJ.opt("feature") instanceof JSONObject) {
                        //handle it when its an object
                        JSONObject f = subJ.getJSONObject("feature");
                        JSONObject rebuild = new JSONObject();
                        Iterator<String> fIter = f.keys();
                        String fKey = "";
                        while(f.hasNext()) {
                            fKey = f.next();
                            rebuild.put(fKey, f.get(fKey));
                        }
                        rebuild.put("level", subJ.get("level"));
                    }
                    else if(subJ.opt("feature") instance of JSONArray) {
                        //handle when its an array
                        //not sure if I want the features to be in an array, or each feature a seperate object...
                    }
                    else throw new RuntimeException("Object 'feature' is not JSONArray or JSONObject in autolevelFormat");
                }
            }
         }
     }

     private void TestingTwo() {
        TestClass ts = new TestClass();
        Log.writel(ts.s);
        TestingTwoPlus(ts);
        Log.writel(ts.s);
     }

     private void TestingTwoPlus(TestClass ts) {
         //ts.setS("Changed.");
         ts = new TestClass();
         ts.setS("Inside function.");
         Log.writel(ts.s);
     }

     private class TestClass {
         String s;

         TestClass() {
             s = "Hello.";
         }

         public void setS(String s) {
             this.s = s;
         }
     }
}