package ore;

import ch.aplu.jgamegrid.*;

import java.util.List;

public class Bulldozer extends Vehicle {
    // destroys clay
    public Bulldozer(String image, Location location, boolean isAuto, List<String> controls, int autoMovementIndex) {
        super("sprites/bulldozer.png", location, isAuto, controls, autoMovementIndex);
    }

    public boolean canMove(Location location) {
        // assuming only one clay can exist in a location at a time
        if (gameGrid.getOneActorAt(location, Clay.class) != null) {
            return collideWithActor(gameGrid.getOneActorAt(location, Clay.class));
        } else if (gameGrid.getOneActorAt(location) == null) {
            return true;
        }
        return false;
    }

    public boolean collideWithActor(Actor actor) {
        if (actor instanceof Clay) {
            ((Clay) gameGrid.getOneActorAt(actor.getLocation(), Clay.class)).removeSelf();
            return true;
        }
        return false;
    }
}
