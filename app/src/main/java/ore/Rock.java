package ore;

import ch.aplu.jgamegrid.Location;

public class Rock extends MapEntity implements Updateable {
    private Location location;
    public Rock(Location location) {
        super(false, "sprites/rock.png", location);
        this.location = location;
    }

    @Override
    public MapGrid update(MapGrid map) {
        // talk to map method and remove myself. Excavator calls this
        Map.remove(this);
        return map;
    }

}
