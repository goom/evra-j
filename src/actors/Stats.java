package evra.actors;

import evra.Log;

import java.util.ArrayList;

class Stats {
    public ArrayList<Stat> statList;
    public ArrayList<Resource> resourceList;

    public Stats () {
        statList = new ArrayList<Stat>();
        resourceList = new ArrayList<Resource>();
    }

    public void addStat(Stat s) {
        statList.add(s);
    }

    public Resource getResource(String ref) {
        for(Resource r : resourceList) {
            if(r.resourceName.equals(ref) || r.resourceAbbr.equals(ref))
                return r;
        }

        Log.error("No such resource " + ref + " found in getResource()");
        return null;
    }

    public void modifyResource(String ref, int cur) {
        this.modifyResource(ref, cur, 0);
    }

    public void modifyResource(String ref, int cur, int max) {
        int index;
        Resource replacer = getResource(ref);
        if(replacer == null)
            return;

        index = resourceList.indexOf(replacer);
        replacer.modifyVal(cur);
        replacer.modifyMax(max);
        resourceList.set(index, replacer);
    }

    public void addResource(Resource r) {
        resourceList.add(r);
    }

    public Stat getStat(String ref) {
        for (Stat s : statList) {
            if(s.statName.equals(ref) || s.statAbbr.equals(ref)) {
                return s;
            }
        }
        Log.error("No such stat " + ref + " found in getStat");
        return null;
    }

    public void setStat(String ref, int val) {
        int index;
        Stat replacer = getStat(ref);
        if(replacer == null)
            return;

        index = statList.indexOf(replacer);
        replacer.setVal(val);
        statList.set(index, replacer);
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

        public void modify(int amount) {
            setVal(value + amount);
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

        public void modifyVal(int amount) {
            setCur(curVal + amount);
        }

        public void modifyMax(int amount) {
            setMax(maxVal + amount);
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