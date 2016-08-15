package evra;

import org.json.*;

public class Spell {
    public String name;
    public String components;
    public String school;
    public String range;
    public String time;
    public String description;
    public String duration;
    public String classes;
    public String id;
    public int level;

    public Spell() {
        name = components = school = range = id = "";
        time = description = classes = duration = "";
        level = 0;
    }

    public Spell(Spell s) {
        name = s.name;
        components = s.components;
        school = s.school;
        range = s.range;
        id = s.id;
        time = s.time;
        description = s.description;
        classes = s.classes;
        duration = s.duration;
        level = s.level;
    }

    public Spell(JSONObject o) {
        name = o.optString("name");
        components = o.optString("components");
        school = o.optString("school");
        id = o.optString("id");
        time = o.optString("time");
        description = o.optString("description");
        duration = o.optString("duration");
        classes = o.optString("classes");
        level = o.optInt("level");
        range = o.optString("range");
    }

    public void write() {
        Log.writel("Name: " + name);
        Log.writel("Duration: " + duration);
        Log.writel("School: " + school);
        Log.writel("Range: " + range);
        Log.writel("Casting Time: " + time);
        Log.writel("Classes: " + classes);
        Log.writel("Level: " + level);
        Log.writel("Components: " + components);
        Log.writel("Description: " + description);
    }
}