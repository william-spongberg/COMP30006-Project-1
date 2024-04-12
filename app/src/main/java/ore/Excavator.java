package ore;

import ch.aplu.jgamegrid.*;

import java.util.List;

/**
 * The Excavator class represents a type of vehicle that destroys rocks.
 * It extends the Vehicle class.
 */
public class Excavator extends Vehicle {
    private int numRockRemoved = 0;

    /**
     * Constructs an Excavator object.
     *
     * @param image             The image of the vehicle.
     * @param location          The initial location of the vehicle.
     * @param isAuto            A flag indicating whether the vehicle is controlled
     *                          automatically or by the keyboard.
     * @param controls          The list of controls for the vehicle.
     * @param autoMovementIndex The index of the automatic movement for the vehicle.
     */
    public Excavator(String image, Location location, boolean isAuto, List<String> controls, int autoMovementIndex) {
        super("sprites/excavator.png", location, isAuto, controls, autoMovementIndex);
    }

    /**
     * Checks if the excavator can move to the specified location.
     * The excavator can move if there is no rock at the location or if it can
     * collide with the rock.
     *
     * @param location the location to check
     * @return true if the excavator can move to the location, false otherwise
     */
    public boolean canMove(Location location) {
        if (gameGrid.getOneActorAt(location, Rock.class) != null) {
            return collideWithActor(gameGrid.getOneActorAt(location, Rock.class));
        } else if (gameGrid.getOneActorAt(location) == null) {
            return true;
        }
        return false;
    }

    /**
     * Collides with the specified actor.
     * If the actor is a Rock, it removes the rock from the game grid.
     *
     * @param actor the actor to collide with
     * @return true if the collision was successful, false otherwise
     */
    public boolean collideWithActor(Actor actor) {
        if (actor instanceof Rock) {
            ((Rock) gameGrid.getOneActorAt(actor.getLocation(), Rock.class)).removeSelf();
            this.incrementNumRockRemoved();
            return true;
        }
        return false;
    }

    /**
     * Returns an array of strings containing the statistics of the excavator.
     * The statistics include the number of moves and the amount of clay removed.
     *
     * @return an array of strings representing the statistics
     */
    public String[] getStatistics() {
        String result[] = {
            "Excavator-" + getId(),
            " Moves: " + this.getNumMoves(),
            "\n",
            "Excavator-" + getId(),
            " Rock removed: " + this.getNumRockRemoved(),
            "\n"
        };
        return result;
    }

    /* getters */

    /**
     * Gets the number of rocks removed by the excavator.
     *
     * @return the number of rocks removed
     */
    public int getNumRockRemoved() {
        return this.numRockRemoved;
    }

    /* setters */

    /**
     * Increments the number of rocks removed by the excavator.
     */
    public void incrementNumRockRemoved() {
        this.numRockRemoved++;
    }
}
