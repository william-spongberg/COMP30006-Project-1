package ore;

import java.util.List;
import java.util.Map;

import ch.aplu.jgamegrid.*;

public class Excavator extends Vehicle {
    public Excavator(String image, Location location) {
        super("sprites/excavator.png", location); // can destroy rocks
    }

    public boolean canMove(Location location) {
        if (getActorsAt(location) instanceof Rock) {
            return collideWithActor(getActorsAt(location));
        } else if (getActorsAt(location) == null) {
            return true;
        }
        return false;
    }

    public boolean collideWithActor(Actor actor) {
        if (actor instanceof Rock) {
            ((Rock) getActorsAt(actor)).removeSelf();
            return true;
        }
        return false;
    }
}
