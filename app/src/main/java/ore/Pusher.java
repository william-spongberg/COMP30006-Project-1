package ore;

import java.util.List;

//import javax.swing.border.Border;

import ch.aplu.jgamegrid.*;

public class Pusher extends Vehicle {
    // pushes ore
    public Pusher(String image, Location location, boolean isAuto, List<String> controls, int autoMovementIndex) {
        super("sprites/pusher.png", location, isAuto, controls, autoMovementIndex);
    }

    public boolean canMove(Location location) {
        // assuming only one ore can exist in a location at a time
        if (gameGrid.getOneActorAt(location, Ore.class) != null) {
            return collideWithActor(gameGrid.getOneActorAt(location, Ore.class));
        } else if (gameGrid.getOneActorAt(location) == null) {
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
        Location currentLocation = ore.getLocation();
        Location nextLocation = ore.getNextMoveLocation();

        // Test if try to move into another actor and actor is not the target
        List<Actor> actorsNext = gameGrid.getActorsAt(nextLocation);
        if (actorsNext != null) {
            for (Actor actor : actorsNext) {
                if (!(actor instanceof Target)) {
                    return false;
                }
            }
        }

        // TODO: Check if ore is pushed into border (colour?)

        // Reset the target if the ore is moved out of target
        List<Actor> actorsCurrent = gameGrid.getActorsAt(currentLocation);
        if (actorsCurrent != null) {
            for (Actor actor : actorsCurrent) {
                if (actor instanceof Target) {
                    Target currentTarget = (Target) actor;
                    currentTarget.show();
                    ore.show(0);
                }
            }
        }

        // Move the ore
        ore.setLocation(nextLocation);

        // Check if we are now at a target
        Target nextTarget = (Target) gameGrid.getOneActorAt(nextLocation, Target.class);
        if (nextTarget != null) {
            ore.show(1);
            nextTarget.hide();
        } else {
            ore.show(0);
        }

        return true;
    }
}
