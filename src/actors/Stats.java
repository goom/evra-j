package evra.actors;

import evra.Log;

import java.util.ArrayList;

class Stats {
    public ArrayList<Stat> list;

    public Stats () {
        list = new ArrayList<Stat>();
    }

    public void addStat(Stat s) {
        list.add(s);
    }

    public Stat getStat(String ref) {
        for (Stat s : list) {
            if(s.statName.equals(ref) || s.statAbbr.equals(ref)) {
                return s;
            }
        }
        Log.error("No such stat " + ref + " found in getStat");
        return null;
    }

    class Stat {
        public String statName;
        public String statAbbr;
        private int value;
        public static final int MAXVAL = 50;

        public Stat(String n, String ab, int val) {
            statName = n;
            statAbbr = ab;
            value = val;
        }

        public Stat() {
            statName = "UNDEFINED";
            statAbbr = "UNID";
            value = 10;
        }

        public int getModifier() {
            return (int) (value - 10) / 2;
        }

        public int getVal() {
            return value;
        }

        public void setVal(int val) {
            if(val < 0)
                val = 0;
            if(val > MAXVAL)
                val = MAXVAL;

            value = val;
        }
    }

    public class Resource {
        public String resourceName;
        public String resourceAbbr;
        private int curVal;
        private int maxVal;

        public Resource(String n, String ab, int cur, int max) {
            resourceName = n;
            resourceAbbr = ab;
            curVal = cur;
            maxVal = max;
        }

        public Resource(String n, String ab, int val) {
            resourceName = n;
            resourceAbbr = ab;
            curVal = maxVal = val;
        }

        public Resource() {
            resourceName = "UNDEFINED";
            resourceAbbr = "UNID";
            curVal = maxVal = 10;
        }

        public int getCur() {
            return curVal;
        }

        public int getMax() {
            return maxVal;
        }

        public void setCur(int val) {
            if(val > maxVal)
                curVal = maxVal;
            else if(val < 0)
                curVal = 0;
            else
                curVal = val;
        }

        public void setMax(int val) {
            if(val < 0)
                val = 0;

            if(curVal > val)
                curVal = val;

            maxVal = val;
        }
    }
}