package ore.entity;

import ch.aplu.jgamegrid.Actor;

/**
 * Rock class, simply an actor that is not rotatable and has the image rock.png as its sprite.
 * all it can do is be destroyed when touched by a Bulldozer, which is handled in the Bulldozer class
 */
public class Rock extends Actor {
    public Rock() {
        super(false, "sprites/rock.png");
    }
}
