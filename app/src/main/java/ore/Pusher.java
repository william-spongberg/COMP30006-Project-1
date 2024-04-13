package ore;

import ch.aplu.jgamegrid.*;

import java.util.List;

//import javax.swing.border.Border;

/**
 * The Pusher class represents a vehicle that pushes ore in a game.
 * It extends the Vehicle class.
 */
public class Pusher extends Vehicle {

    /**
     * Constructs a Pusher object.
     *
     * @param isAuto            A flag indicating whether the vehicle is controlled
     *                          automatically or by the keyboard.
     * @param controls          The list of controls for the vehicle.
     * @param autoMovementIndex The index of the automatic movement for the vehicle.
     */
    public Pusher(boolean isAuto, List<String> controls, int autoMovementIndex) {
        super("sprites/pusher.png", isAuto, controls, autoMovementIndex);
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
        } else if ((gameGrid.getOneActorAt(location) == null) || (gameGrid.getOneActorAt(location, Target.class) != null)) {
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

        // Move the ore to the next location
        ore.checkAndMove(nextLocation);

        return true;
    }

    /**
     * Retrieves the statistics of the Pusher object.
     * 
     * @return an array of strings containing the statistics
     */
    public String[] getStatistics() {
        String[] result = {
                "Pusher-" + getId(),
                " Moves: " + this.getNumMoves(),
                "\n"
        };
        return result;
    }
}
