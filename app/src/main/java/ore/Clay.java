package ore;

import ch.aplu.jgamegrid.Location;

public class Clay extends MapEntity implements Updateable
{
    public Clay(Location location) {
        super(false, "sprites/clay.png", location);
    }
}
