package ore;

import java.util.List;
import java.util.Map;

import ch.aplu.jgamegrid.*;

public class Bulldozer extends Vehicle {
    public Bulldozer(String image, Location location) {
        super("sprites/bulldozer.png", location); // same as excavator except can destroy clay
    }

    public boolean canMove(Location location) {
        if (getActorsAt(location) instanceof Clay) {
            return collideWithActor(getActorsAt(location));
        } else if (getActorsAt(location) == null) {
            return true;
        }
        return false;
    }

    public boolean collideWithActor(Actor actor) {
        if (actor instanceof Clay) {
            ((Clay) getActorsAt(actor)).removeSelf();
            return true;
        }
        return false;
    }
}
