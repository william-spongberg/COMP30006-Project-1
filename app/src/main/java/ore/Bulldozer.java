package ore;

import ch.aplu.jgamegrid.*;

import java.util.List;

/**
 * The Bulldozer class represents a type of vehicle that destroys clay.
 * It extends the Vehicle class.
 */
public class Bulldozer extends Vehicle {
    private static int id = 0;
    private int numClayRemoved = 0;

    /**
     * Constructs a Bulldozer object.
     *
     * @param isAuto            A flag indicating whether the vehicle is controlled
     *                          automatically or by the keyboard.
     * @param controls          The list of controls for the vehicle.
     * @param autoMovementIndex The index of the automatic movement for the vehicle.
     */
    public Bulldozer(boolean isAuto, List<String> controls, int autoMovementIndex) {
        super("sprites/bulldozer.png", isAuto, controls, autoMovementIndex);
        this.setIsAuto(controls, 'B');
        incrementId();

        System.out.println("\nBulldozer " + getId() + ":");
        System.out.println("            isAuto: " + isAuto);
        System.out.println("            controls: " + controls);
        System.out.println("            autoMovementIndex: " + autoMovementIndex + "\n");
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
        } else if (gameGrid.getOneActorAt(location) == null) {
            return true;
        }
        return false;
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
            this.incrementNumClayRemoved();
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
        String result[] = {
            "Bulldozer-" + getId() + " Moves: " + this.getNumMoves(),
            "Bulldozer-" + getId() + " Clay removed: " + this.getNumClayRemoved()
    };
        return result;
    }

    /* getters */

    /**
     * Returns the ID of the vehicle.
     *
     * @return the ID of the vehicle
     */
    public static int getId() {
        return id;
    }

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
     * Increments the ID of the vehicle.
     */
    public static void incrementId() {
        id++;
    }

    /**
     * Increments the number of clay removed by the bulldozer.
     */
    public void incrementNumClayRemoved() {
        this.numClayRemoved++;
    }
}
