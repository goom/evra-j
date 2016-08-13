package evra.testing;

import evra.math.*;
import evra.*;
import evra.json.*;

import java.util.ArrayList;
import java.math.*;

public class Test {
    public Test() {
        Log.writel("ID 10001.name: " + Spells.getFromID("10001").name);
        Spells.generateID();
        Log.writel("ID 10001.name: " + Spells.getFromID("10001").name);

        Spell sp = new Spell(Spells.getFromID("1"));
        Log.writel(sp.name);
        Log.writel(sp.description);
     }
}