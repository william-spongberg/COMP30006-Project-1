package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;


public class Target extends MapObject
{
    private Location location;
    public Target(Location location) {
        super(false, "sprites/target.gif", location);
        this.location = location;
    }
}
