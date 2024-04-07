package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class Ore extends MapObject
{
    private Location location;
    public Ore(Location location) {
        super(false, "sprites/ore_0.png", location);
        this.location = location;
    }

    // call this every time we move
    private OreCart checkIfExists() {
        if (getMapObject(location) == instanceof(Target)){
            // delete self from map hashmap

            // create an orecart
            // return the orecart (and append to map hashmap)
        }

    }

    // only call if moved by a pusher
    private boolean canMove() {

    }

    private void update () {
        // called when ?
    }
}
