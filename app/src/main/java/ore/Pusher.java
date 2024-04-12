package ore;

import java.util.List;

//import javax.swing.border.Border;

import ch.aplu.jgamegrid.*;

/**
 * The Pusher class represents a vehicle that pushes ore in a game.
 * It extends the Vehicle class.
 */
public class Pusher extends Vehicle {

    /**
     * Constructs a Pusher object.
     *
     * @param image             The image of the vehicle.
     * @param location          The initial location of the vehicle.
     * @param isAuto            A flag indicating whether the vehicle is controlled
     *                          automatically or by the keyboard.
     * @param controls          The list of controls for the vehicle.
     * @param autoMovementIndex The index of the automatic movement for the vehicle.
     */
    public Pusher(String image, Location location, boolean isAuto, List<String> controls, int autoMovementIndex) {
        super("sprites/pusher.png", location, isAuto, controls, autoMovementIndex);
    }

    /**
     * Checks if the Pusher can move to the specified location.
     *
     * @param location the location to check
     * @return true if the Pusher can move to the location, false otherwise
     */
    public boolean canMove(Location location) {
        // assuming only one ore can exist in a location at a time
        if (gameGrid.getOneActorAt(location, Ore.class) != null) {
            return collideWithActor(gameGrid.getOneActorAt(location, Ore.class));
        } else if (gameGrid.getOneActorAt(location) == null) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the Pusher collides with the specified actor.
     *
     * @param actor the actor to check collision with
     * @return true if the Pusher collides with the actor, false otherwise
     */
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
     * Moves the specified ore to the next location.
     *
     * @param ore the ore to move
     * @return true if the ore is successfully moved, false otherwise
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


    /**
     * Returns an array of statistics related to the Pusher object.
     *
     * @return the number of moves made by the Pusher
     */
    public String[] getStatistics() {
        String [] result = {
            "Pusher-" + getId(),
            " Moves: " + this.getNumMoves(),
            "\n"
        };
        return result;
    }
}
