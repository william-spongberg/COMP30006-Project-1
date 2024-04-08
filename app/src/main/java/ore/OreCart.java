package ore;

import ch.aplu.jgamegrid.Location;

public class OreCart extends MapEntity implements Updateable{
    // check if this is pushable

    // check if this is on the target
    private Location location;
    public OreCart(Location location) {
        super(false, "sprites/ore_1.png", location);
        this.location = location;
    }

    // call this every time we move

}
