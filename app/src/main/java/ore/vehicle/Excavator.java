package ore.vehicle;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import ore.object.Rock;

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
     * @param isAuto        A flag indicating whether the vehicle is controlled
     *                      automatically or by the keyboard.
     * @param moves         The list of controls for the vehicle.
     * @param movementIndex The index of the movement for the vehicle.
     */
    public Excavator(int id, boolean isAuto, List<String> moves, int movementIndex) {
        super("sprites/excavator.png", id, isAuto, moves, movementIndex);
        setIsAuto(moves, 'E');

        System.out.println("\nExcavator " + getId() + ":");
        System.out.println("            isAuto: " + isAuto);
        System.out.println("            controls: " + moves);
        System.out.println("            autoMovementIndex: " + movementIndex + "\n");
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
        } else
            return gameGrid.getOneActorAt(location) == null;
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
            actor.removeSelf();
            incrementNumRockRemoved();
            return true;
        }
        return false;
    }

    /* getters */

    /**
     * Returns an array of strings containing the statistics of the Excavator.
     * The statistics include the Excavator's ID, number of moves, and amount of
     * rock removed.
     *
     * @return an array of strings representing the statistics of the Excavator
     */
    public String[] getStatistics() {
        String[] result = {
                "Excavator-" + getId() + " Moves: " + getNumMoves(),
                "Excavator-" + getId() + " Rock removed: " + getNumRockRemoved()
        };
        return result;
    }

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
