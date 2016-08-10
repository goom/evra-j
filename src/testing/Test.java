package evra.testing;

import evra.Log;

import java.util.ArrayList;
import java.math.*;

public class Test {
    public ArrayList<String> list;

    public Test() {
        list = new ArrayList<String>();
        for(int x = 0; x < 50; x++)
            list.add(Integer.toString(x));

        int index = 0;
        for(String z : list) {
            if(z.equals("26")) {
                index = list.indexOf(z);
            }
        }

        list.set(index, "1000");

        for(String a : list) {
            Log.write("Contains: " + a);
        }
     }
}