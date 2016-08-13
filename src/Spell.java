package evra;

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

    public static void write(Spell s) {
        Log.writel("Name: " + s.name);
        Log.writel("Duration: " + s.duration);
        Log.writel("School: " + s.school);
        Log.writel("Range: " + s.range);
        Log.writel("Casting Time: " + s.time);
        Log.writel("Classes: " + s.classes);
        Log.writel("Level: " + s.level);
        Log.writel("Components: " + s.components);
        Log.writel("Description: " + s.description);
    }
}