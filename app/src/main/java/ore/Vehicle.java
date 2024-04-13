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
    private VehicleController controller = null;
    private boolean isAuto = false;
    private static int id = 0;
    private int numMoves = 0;

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
        this.isAuto = isAuto;
        incrementID();

        if (isAuto) {
            this.controller = new AutomaticController(this, controls, autoMovementIndex);
        } else {
            this.controller = new KeyboardController(this);
        }

        System.out.println("\nVehicle " + getId() + " created");
        System.out.println("Vehicle isAuto: " + isAuto);
        System.out.println("Vehicle controls: " + controls);
        System.out.println("Vehicle autoMovementIndex: " + autoMovementIndex + "\n");
    }

    /**
     * Moves the vehicle to the next location based on the current mode.
     * If the vehicle is in auto mode, it moves to the next location returned by the
     * controller's autoMoveNext method.
     * If the vehicle is in manual mode, it moves to the next location returned by
     * the controller's manualMoveNext method.
     */
    public void moveVehicle() {
        if (this.isAuto) {
            moveToLocation(controller.autoMoveNext());
        } else {
            moveToLocation(controller.manualMoveNext());
        }
    }

    /**
     * Moves the vehicle to the specified location if it is not null and can be
     * moved to.
     * If the vehicle is currently on a target, the target is shown.
     * The vehicle's location is updated and the vehicle is set as the controller's
     * vehicle.
     *
     * @param location the location to move the vehicle to
     */
    public void moveToLocation(Location location) {
        if (location != null && !(this.gameGrid.getBg().getColor(location).equals(OreSim.BORDER_COLOUR))
                && !(this.gameGrid.getBg().getColor(location).equals(OreSim.OUTSIDE_COLOUR))
                && canMove(location)) {
            Target curTarget = (Target) this.gameGrid.getOneActorAt(this.getLocation(), Target.class);
            if (curTarget != null) {
                curTarget.show();
            }
            this.setLocation(location);
            this.controller.setVehicle(this);
            this.incrementNumMoves();
        }
    }

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

    /**
     * Returns an array of strings representing the statistics of the vehicle.
     * 
     * @return An array of strings representing the statistics.
     */
    public abstract String[] getStatistics();

    /* getters */

    /**
     * Gets the controller of the vehicle.
     * 
     * @return The controller of the vehicle.
     */
    public VehicleController getController() {
        return this.controller;
    }

    /**
     * Returns the value indicating whether the vehicle is automatic or not.
     *
     * @return true if the vehicle is automatic, false otherwise
     */
    public boolean getIsAuto() {
        return this.isAuto;
    }

    /**
     * Returns the ID of the vehicle.
     *
     * @return the ID of the vehicle
     */
    public static int getId() {
        return id;
    }

    /**
     * Returns the number of moves made by the vehicle.
     *
     * @return the number of moves made by the vehicle
     */
    public int getNumMoves() {
        return this.numMoves;
    }

    /* setters */

    /**
     * Sets the controller of the vehicle.
     * 
     * @param controller The controller to set.
     */
    public void setController(VehicleController controller) {
        this.controller = controller;
    }

    /**
     * Sets the value indicating whether the vehicle is automatic or manual.
     *
     * @param isAuto true if the vehicle is automatic, false if it is manual
     */
    public void setIsAuto(boolean isAuto) {
        this.isAuto = isAuto;
    }

    /**
     * Increments the ID of the vehicle by 1.
     */
    public void incrementID() {
        id += 1;
    }

    /**
     * Increments the number of moves for the vehicle.
     */
    public void incrementNumMoves() {
        this.numMoves++;
    }
}