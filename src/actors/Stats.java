package evra.actors;

import evra.Log;

import java.util.ArrayList;

class Stats {
    private ArrayList<Stat> statList;
    private ArrayList<Resource> resourceList;

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
        replacer.curVal += cur;
        replacer.maxVal += max;
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

    public void modifyStat(String ref, int amount) {
        int index;
        Stat replacer = getStat(ref);
        if(replacer == null)
            return;

        index = statList.indexOf(replacer);
        replacer.value += amount;
        statList.set(index, replacer);
    }

    public void setStat(String ref, int val) {
        int index;
        Stat replacer = getStat(ref);
        if(replacer == null)
            return;

        index = statList.indexOf(replacer);
        replacer.value = val;
        statList.set(index, replacer);
    }

    private class Stat {
        public String statName;
        public String statAbbr;
        public int value;
        public static final int MAXVAL = 50;

        public Stat(String n, String ab, int val) {
            statName = n;
            statAbbr = ab;
            value = val;
        }
    }

    private class Resource {
        public String resourceName;
        public String resourceAbbr;
        public int curVal;
        public int maxVal;

        public Resource(String n, String ab, int cur, int max) {
            resourceName = n;
            resourceAbbr = ab;
            curVal = cur;
            maxVal = max;
        }
    }
}