package evra;

import evra.actors.*;

class Tracker {


    private class Token {
        public final Actor owner;
        public int initiative;

        public Token(final Actor a) {
            owner = a;
            //initiative = owner.rollInitiative();
        }
    }
}