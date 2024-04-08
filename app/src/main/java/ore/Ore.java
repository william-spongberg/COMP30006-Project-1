package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;

public class Ore extends MapEntity implements Updateable
{
    private Location location;
    public Ore(Location location) {
        super(false, "sprites/ore_0.png", location);
        this.location = location;
    }

    @Override
    public MapGrid update(MapGrid map) {
        ArrayList<MapEntity> objectAtLoc = map.get(this.getLocation());
        if (objectAtLoc != null && !objectAtLoc.isEmpty()) {
            MapEntity obj = objectAtLoc.get(this.getLocation());
            if (obj instanceof Target) {
                Location newSpawn = this.getLocation();
                removeSelf();
                // ?
                map.addToHashMap(new OreCart(newSpawn));
            }
        }
        // else, we need to update location
    }

    /* might not need this actually
    private OreCart checkIfExists() {
        if (getMapObject(location) == instanceof(Target)){
            // delete self from map hashmap

            // create an orecart
            // return the orecart (and append to map hashmap)
        }

    }
    */

}
