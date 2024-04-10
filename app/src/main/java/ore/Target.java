package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;


public class Target extends Actor
{
    private Location location;
    public Target(boolean isRotatable, String filename) {
        super(false, "sprites/target.gif");
    }
}
