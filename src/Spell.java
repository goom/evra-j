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
    public int level;

    public Spell() {
        name = components = school = range = "";
        time = description = classes = duration = "";
        level = 0;
    }
}