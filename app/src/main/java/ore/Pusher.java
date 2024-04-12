package ore;

import java.util.List;
import java.util.Map;

import javax.swing.border.Border;

import ch.aplu.jgamegrid.*;

public class Pusher extends Vehicle {
    public Pusher(String image, Location location) {
        super("sprites/pusher.png", location);
    }

    public boolean canMove(Location location) {
        if (getActorsAt(location) instanceof Ore) {
            return collideWithActor(getActorsAt(location));
        } else if (getActorsAt(location) == null) {
            return true;
        }
        return false;
    }

    public boolean collideWithActor(Actor actor) {
        if (actor instanceof Ore) {
            Ore ore = (Ore) actor;
            // try to move the ore
            ore.setDirection(this.getDirection());
            return moveOre(ore);
        }
        return false;
    }

    /**
     * When the pusher pushes the ore in 1 direction, this method will be called to
     * check if the ore can move in that direction
     * and if it can move, then it changes the location
     * 
     * @param ore
     * @return
     */
    private boolean moveOre(Ore ore) {

        Location next = ore.getNextMoveLocation();

        // Test if try to move into another actor and actor is not the target
        if (!(getActorsAt(next) instanceof Target) && getActorsAt(next) != null) {
            return false;
        }

        // Reset the target if the ore is moved out of target
        Location currentLocation = ore.getLocation();

        List<Actor> actors = getActorsAt(currentLocation);
        if (actors != null) {
            for (Actor actor : actors) {
                if (actor instanceof Target) {
                    Target currentTarget = (Target) actor;
                    currentTarget.show();
                    ore.show(0);
                }
            }
        }

        // Move the ore
        ore.setLocation(next);

        // Check if we are at a target
        Target nextTarget = (Target) getOneActorAt(next, Target.class);
        if (nextTarget != null) {
            ore.show(1);
            nextTarget.hide();
        } else {
            ore.show(0);
        }

        return true;
    }
}
