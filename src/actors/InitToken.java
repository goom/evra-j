package evra.actors;

import evra.actors.*;

public class InitToken {
    public final Actor actor;
    public final String name;
    public int initiative;

    public InitToken(final Actor a, int init) {
        actor = a;
        initiative = init;
        name = actor.getName();
    }

    public InitToken(final String s, int init) {
        name = s;
        initiative = init;
        actor = null;
    }
}