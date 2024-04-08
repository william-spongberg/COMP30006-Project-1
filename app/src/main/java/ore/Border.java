package ore;

import ch.aplu.jgamegrid.Location;

public class Border extends MapObject{

    Location location;
    public Border(Location location) {
        super(false, "sprites/border.png", location);
        this.location = location;
    }
}
