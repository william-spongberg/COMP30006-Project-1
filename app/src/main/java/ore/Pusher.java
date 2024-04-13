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
        setIsAuto(controls, 'P');
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
        } else if ((gameGrid.getOneActorAt(location) == null)
                || (gameGrid.getOneActorAt(location, Target.class) != null)) {
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
            return ore.checkAndMove(ore.getNextMoveLocation());
        }
        return false;
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
