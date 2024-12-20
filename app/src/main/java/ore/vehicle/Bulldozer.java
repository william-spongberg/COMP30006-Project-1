package ore.vehicle;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import ore.entity.Clay;

import java.util.List;

/**
 * The Bulldozer class represents a type of vehicle that destroys clay.
 * It extends the Vehicle class.
 */
public class Bulldozer extends Vehicle {
    private int numClayRemoved = 0;

    /**
     * Constructs a Bulldozer object.
     *
     * @param isAuto        A flag indicating whether the vehicle is controlled
     *                      automatically or by the keyboard.
     * @param moves         The list of controls for the vehicle.
     * @param movementIndex The index of the movement for the vehicle.
     */
    public Bulldozer(int id, boolean isAuto, List<String> moves, int movementIndex) {
        super("sprites/bulldozer.png", id, isAuto, moves, movementIndex);
        setIsAuto(moves, 'B');

        System.out.println("\nBulldozer " + getId() + ":");
        System.out.println("            isAuto: " + isAuto);
        System.out.println("            controls: " + moves);
        System.out.println("            autoMovementIndex: " + movementIndex + "\n");
    }

    /**
     * Checks if the bulldozer can move to the specified location.
     * The bulldozer can move if there is no clay at the location or if it can
     * collide with the clay.
     *
     * @param location the location to check
     * @return true if the Bulldozer can move to the location, false otherwise
     */
    public boolean canMove(Location location) {
        if (gameGrid.getOneActorAt(location, Clay.class) != null) {
            return collideWithActor(gameGrid.getOneActorAt(location, Clay.class));
        } else
            return gameGrid.getOneActorAt(location) == null;
    }

    /**
     * Collides with the specified actor.
     * If the actor is of type Clay, it removes the clay from the game grid.
     *
     * @param actor the actor to collide with
     * @return true if the collision was successful, false otherwise
     */
    public boolean collideWithActor(Actor actor) {
        if (actor instanceof Clay) {
            actor.removeSelf();
            incrementNumClayRemoved();
            return true;
        }
        return false;
    }

    /* getters */

    /**
     * Returns an array of strings containing the statistics of the Bulldozer.
     * The statistics include the Bulldozer's ID, number of moves, and amount of
     * clay removed.
     *
     * @return an array of strings representing the statistics of the Bulldozer
     */
    public String[] getStatistics() {
        String[] result = {
                "Bulldozer-" + getId() + " Moves: " + getNumMoves(),
                "Bulldozer-" + getId() + " Clay removed: " + getNumClayRemoved()
        };
        return result;
    }

    /* getters */

    /**
     * Gets the number of rocks removed by the excavator.
     *
     * @return the number of rocks removed
     */
    public int getNumClayRemoved() {
        return this.numClayRemoved;
    }

    /* setters */

    /**
     * Increments the number of clay removed by the bulldozer.
     */
    public void incrementNumClayRemoved() {
        this.numClayRemoved++;
    }
}
