package ore;

import ch.aplu.jgamegrid.Location;

public class Floor extends MapEntity{

    Location location;
    public Floor(Location location) {
        super(false, "sprites/Floor.png", location);
        this.location = location;
    }
}
