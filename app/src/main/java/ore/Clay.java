package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class Clay extends MapObject implements Updateable
{
    private Location location;
    public Clay(Location location) {
        super(false, "sprites/clay.png", location);
        this.location = location;
    }

}
