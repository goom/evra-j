package evra;

import evra.Log;
import java.util.*;

public class SpellList {
    private static ArrayList<Spell> list;

    public static void init() {
        list =  new ArrayList<Spell>();
    }

    public static void addSpell(String n, String comp, String school,
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
}