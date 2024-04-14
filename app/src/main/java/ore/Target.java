package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

/**
 * target class, simply an actor that is not rotatable and has the image target.png as its sprite.
 * Its location is checked by Ore to see if an Ore is going to move onto one.
 */
public class Target extends Actor
{
    private Location location;
    public Target() {
        super(false, "sprites/target.gif");
    }
}
