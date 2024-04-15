package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.List;


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
    public Pusher(int id, boolean isAuto, List<String> controls, int autoMovementIndex) {
        super("sprites/pusher.png", id, isAuto, controls, autoMovementIndex);
        setIsAuto(controls, 'P');

        System.out.println("\nPusher " + getId() + ":");
        System.out.println("            isAuto: " + isAuto);
        System.out.println("            controls: " + controls);
        System.out.println("            autoMovementIndex: " + autoMovementIndex + "\n");
    }

    /**
     * Checks if the Pusher can move to the specified location.
     *
     * @param location the location to check
     * @return true if the Pusher can move to the location, false otherwise
     */
    @Override
    public boolean canMove(Location location) {
        // assuming only one ore can exist in a location at a time
        if (gameGrid.getOneActorAt(location, Ore.class) != null) {
            return collideWithActor(gameGrid.getOneActorAt(location, Ore.class));
        } else return (gameGrid.getOneActorAt(location) == null)
                || (gameGrid.getOneActorAt(location, Target.class) != null);
    }

    /**
     * Checks if the Pusher collides with the specified actor.
     *
     * @param actor the actor to check collision with
     * @return true if the Pusher collides with the actor, false otherwise
     */
    @Override
    public boolean collideWithActor(Actor actor) {
        if (actor instanceof Ore ore) {
            // try to move the ore
            ore.setDirection(getDirection());
            return ore.checkAndMove(ore.getNextMoveLocation());
        }
        return false;
    }

    /* getters */

    /**
     * Retrieves the statistics of the Pusher object.
     *
     * @return an array of strings containing the statistics
     */
    @Override
    public String[] getStatistics() {
        String[] result = {
                "Pusher-" + getId() + " Moves: " + getNumMoves()
        };
        return result;
    }
}
