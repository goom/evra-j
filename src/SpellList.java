package evra;

import evra.Log;
import java.util.*;
import java.io.*;

public class SpellList {
    private static ArrayList<Spell> list;

    public static void init() {
        list =  new ArrayList<Spell>();
    }

    public static void add(String n, String comp, String school,
                        String range, String time, String desc,
                        String cl, String dur, int lvl) {
        Spell add = new Spell();
        add.name = n;
        add.components = comp;
        add.school = school;
        add.range = range;
        add.time = time;
        add.description = desc;
        add.classes = cl;
        add.duration = dur;
        add.level = lvl;

        list.add(add);
    }
    public static void add(Spell sp) {   
        list.add(sp);
    }

    public static int size() {
        return list.size();
    }

    public static void addID() {
        String inp = "";
        Console c = System.console();
        for(Spell x : list) {
            Log.writel(x.name);
            Log.write("ID: ");
            inp = c.readLine();
            int i = list.indexOf(x);
            x.id = inp;
            list.set(i, x);
        }
    }
}