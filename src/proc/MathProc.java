package evra.proc;

import evra.math.*;

public class MathProc {
    //Holds the loop/procedure information for the math mode

    public MathProc(String cmd) {
        Roll.eval(cmd, true);
        //super simple... might do more in the future, meh
    }
}