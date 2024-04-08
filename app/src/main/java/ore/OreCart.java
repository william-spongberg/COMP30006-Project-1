package ore;

import ch.aplu.jgamegrid.Location;

public class OreCart extends MapObject{
    // check if this is pushable

    // check if this is on the target
    private Map map;
    private Location location;
    public OreCart(Location location) {
        super(false, "sprites/ore_1.png", location);
        this.location = location;
    }

    // call this every time we move
    private Ore checkIfExists() {
        if (getMapObject(location) != instanceof(Target)) {
            // delete self from map hashmap

            // create an ore
            // return the ore (and append to map hashmap)
        }
    }
}
