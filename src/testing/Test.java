package evra.testing;

import evra.Log;
import evra.math.*;

import java.util.ArrayList;
import java.math.*;

public class Test {
    public ArrayList<String> list;

    public Test() {
        Roll c = new Roll("3d20");
        c.eval(true);
     }
}