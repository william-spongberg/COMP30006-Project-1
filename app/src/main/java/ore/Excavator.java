package ore;

import ch.aplu.jgamegrid.*;

import java.util.List;

public class Excavator extends Vehicle {
    // destroys rocks
    public Excavator(String image, Location location, boolean isAuto, List<String> controls, int autoMovementIndex) {
        super("sprites/excavator.png", location, isAuto, controls, autoMovementIndex);
    }

    public boolean canMove(Location location) {
        // assuming only one rock can exist in a location at a time
        if (gameGrid.getOneActorAt(location, Rock.class) != null) {
            return collideWithActor(gameGrid.getOneActorAt(location, Rock.class));
        } else if (gameGrid.getOneActorAt(location) == null) {
            return true;
        }
        return false;
    }

    public boolean collideWithActor(Actor actor) {
        if (actor instanceof Rock) {
            ((Rock) gameGrid.getOneActorAt(actor.getLocation(), Rock.class)).removeSelf();
            return true;
        }
        return false;
    }
}
