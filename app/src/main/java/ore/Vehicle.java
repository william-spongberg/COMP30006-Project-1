package ore;

import ch.aplu.jgamegrid.*;

import java.util.List;

/**
 * Represents a vehicle in the game.
 * 
 * This class is an abstract class that extends the `Actor` class.
 * It provides common functionality and properties for all vehicles in the game.
 */
public abstract class Vehicle extends Actor {
    /**
     * Constructs a new Vehicle object.
     * 
     * @param image             The image of the vehicle.
     * @param isAuto            A flag indicating whether the vehicle is controlled
     *                          automatically or by the keyboard.
     * @param controls          The list of controls for the vehicle.
     * @param autoMovementIndex The index of the automatic movement for the vehicle.
     */
    public Vehicle(String image, boolean isAuto, List<String> controls, int autoMovementIndex) {
        super(true, image);
        setIsAuto(isAuto);

        if (isAuto) {
            setController(new AutomaticController(this, controls, autoMovementIndex));
        } else {
            setController(new KeyboardController(this));
        }
    }

    /**
     * Sets the automatic status of the vehicle based on the provided controls and
     * character.
     * If the controls list is not empty and contains the specified character, the
     * automatic status is set to true.
     *
     * @param controls the list of controls to check
     * @param c        the character to search for in the controls list
     */
    public void setIsAuto(List<String> controls, char c) {
        if (!(controls.isEmpty())) {
            for (String s : controls) {
                if (s.indexOf(c) != -1) {
                    setIsAuto(true);
                }
            }
        }
    }

    /**
     * Moves the vehicle to the next location based on the current mode.
     * If the vehicle is in auto mode, it moves to the next location returned by the
     * controller's autoMoveNext method.
     * If the vehicle is in manual mode, it moves to the next location returned by
     * the controller's manualMoveNext method.
     */
    public void moveVehicle() {
        if (getIsAuto()) {
            moveToLocation(getController().autoMoveNext());
        } else {
            moveToLocation(getController().manualMoveNext());
        }
    }

    /**
     * Moves the vehicle to the specified location if it is a valid move.
     * 
     * @param location the location to move the vehicle to
     */
    public void moveToLocation(Location location) {
        if (location != null && !(gameGrid.getBg().getColor(location).equals(OreSim.BORDER_COLOUR))
                && !(gameGrid.getBg().getColor(location).equals(OreSim.OUTSIDE_COLOUR))
                && canMove(location)) {

            updateTargets(location);
            setLocation(location);
            getController().setVehicle(this);
            incrementNumMoves();
        }
    }

    /**
     * Updates the targets based on the given location.
     * Shows the targets at the current location and hides the targets at the new
     * location.
     *
     * @param location the new location to update the targets
     */
    private void updateTargets(Location location) {
        List<Actor> actors = gameGrid.getActorsAt(getLocation());
        for (Actor actor : actors) {
            if (actor instanceof Target) {
                Target target = (Target) actor;
                target.show();
            }
        }

        actors = this.gameGrid.getActorsAt(location);
        for (Actor actor : actors) {
            if (actor instanceof Target) {
                Target target = (Target) actor;
                target.hide();
            }
        }
    }

    /* abstract methods */

    /**
     * Checks if the vehicle can move to the specified location.
     * 
     * @param location The location to check.
     * @return true if the vehicle can move to the location, false otherwise.
     */
    public abstract boolean canMove(Location location);

    /**
     * Checks if the vehicle collides with the specified actor.
     * 
     * @param actor The actor to check collision with.
     * @return true if the vehicle collides with the actor, false otherwise.
     */
    public abstract boolean collideWithActor(Actor actor);

    /* getters */

    /**
     * Returns an array of strings representing the statistics of the vehicle.
     * 
     * @return An array of strings representing the statistics.
     */
    public abstract String[] getStatistics();

    /**
     * Returns the controller for the vehicle.
     *
     * @return the controller for the vehicle
     */
    public abstract VehicleController getController();

    /**
     * Returns a boolean value indicating whether the vehicle is automatic or not.
     *
     * @return true if the vehicle is automatic, false otherwise
     */
    public abstract boolean getIsAuto();

    /**
     * Returns the number of moves made by the vehicle.
     *
     * @return the number of moves made by the vehicle
     */
    public abstract int getNumMoves();

    /* setters */

    /**
     * Sets the controller for the vehicle.
     *
     * @param controller the controller to be set
     */
    public abstract void setController(VehicleController controller);

    /**
     * Sets the value indicating whether the vehicle is automatic or manual.
     * 
     * @param isAuto true if the vehicle is automatic, false if it is manual
     */
    public abstract void setIsAuto(boolean isAuto);

    /**
     * Increments the number of moves made by the vehicle.
     * This method should be implemented by subclasses to update the number of moves
     * specific to each type of vehicle.
     */
    public abstract void incrementNumMoves();
}