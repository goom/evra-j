package evra.actor;

abstract class Actor {
    public String name;

    public Actor(String n) {
        name = n;
    }

    abstract int attack();
    abstract int defend();
}