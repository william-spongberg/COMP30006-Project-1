package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class Rock extends MapObject implements Updateable {
    private Location location;
    public Rock(Location location) {
        super(false, "sprites/rock.png", location);
        this.location = location;
    }

    @Override
    public void update() {
        // talk to map method and remove myself. Excavator calls this
        Map.remove(this);
    }

}
