package evra.testing;

import evra.Log;
import evra.math.*;

import java.util.ArrayList;
import java.math.*;

public class Test {
    public ArrayList<String> list;

    public Test() {
        Calc c = new Calc("3d20");
        c.eval(true);
     }
}