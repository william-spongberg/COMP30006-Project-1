package ore;

import ch.aplu.jgamegrid.Actor;

/**
 * Clay class, simply an actor that is not rotatable and has the image clay.png as its sprite.
 * all it can do is be destroyed when touched by a Excavator, which is handled in the excavator class
 */
public class Clay extends Actor {

    public Clay() {
        super(false, "sprites/clay.png");
    }
}
