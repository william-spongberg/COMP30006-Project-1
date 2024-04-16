package ore.controller;

import ch.aplu.jgamegrid.GGKeyListener;
import ch.aplu.jgamegrid.Location;
import ore.vehicle.Vehicle;

import java.util.List;
import java.awt.event.KeyEvent;

/**
 * The `KeyboardController` class implements the `VehicleController` and
 * `GGKeyListener` interfaces to control a vehicle using keyboard input.
 * It allows the user to control the vehicle's movement by pressing arrow keys
 * on the keyboard.
 */
public class KeyboardController implements VehicleController, GGKeyListener {
    private Vehicle vehicle = null;
    private boolean isFinished = false;
    private Location nextLocation = null;

    /**
     * Constructs a new KeyboardController object with the specified vehicle.
     *
     * @param vehicle the vehicle to be controlled
     */
    public KeyboardController(Vehicle vehicle) {
        this.setVehicle(vehicle);
    }

    /*
     * Returns the next location to move to based on the last key pressed.
     * Sets the next location to null to avoid moving to the same location.
     */
    public Location nextMove() {
        Location next = this.getNextLocation();
        // set to null to avoid moving to the same location
        this.setNextLocation(null);
        return next;
    }

    /**
     * This method is automatically called by the framework when a key is pressed.
     * Based on the pressed key, the pusher will change the direction and move.
     *
     * @param evt the KeyEvent object representing the key press event
     * @return the next location to move to, or null if no valid location
     */
    public boolean keyPressed(KeyEvent evt) {
        if (this.getIsFinished())
            return false;

        Location next = null;
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                next = this.getVehicle().getLocation().getNeighbourLocation(Location.WEST);
                this.getVehicle().setDirection(Location.WEST);
                break;
            case KeyEvent.VK_UP:
                next = this.getVehicle().getLocation().getNeighbourLocation(Location.NORTH);
                this.getVehicle().setDirection(Location.NORTH);
                break;
            case KeyEvent.VK_RIGHT:
                next = this.getVehicle().getLocation().getNeighbourLocation(Location.EAST);
                this.getVehicle().setDirection(Location.EAST);
                break;
            case KeyEvent.VK_DOWN:
                next = this.getVehicle().getLocation().getNeighbourLocation(Location.SOUTH);
                this.getVehicle().setDirection(Location.SOUTH);
                break;
        }

        if (next != null) {
            this.setNextLocation(next);
            return true;
        }

        return false;
    }

    /**
     * Handles the key released event.
     *
     * @param evt the KeyEvent object representing the key release event
     * @return false as key releases are not supported in this controller
     */
    public boolean keyReleased(KeyEvent evt) {
        return false;
    }

    /* getters */

    /**
     * Retrieves the next location.
     *
     * @return The next location.
     */
    public Location getNextLocation() {
        return this.nextLocation;
    }

    /**
     * Get the associated vehicle.
     *
     * @return The associated vehicle.
     */
    public Vehicle getVehicle() {
        return this.vehicle;
    }

    /**
     * Check if the controller has finished its operation.
     *
     * @return True if the controller has finished, false otherwise.
     */
    public boolean getIsFinished() {
        return this.isFinished;
    }

    /**
     * Get the list of moves.
     *
     * @return null since done manually.
     */
    public List<String> getMoves() {
        return null;
    }

    /**
     * Get the index for automatic movement.
     *
     * @return The index for automatic movement.
     */
    public int getMovementIndex() {
        return -1;
    }

    /* setters */

    /**
     * Sets the next location.
     *
     * @param nextLocation the next location to set
     */
    public void setNextLocation(Location nextLocation) {
        this.nextLocation = nextLocation;
    }

    /**
     * Set the associated vehicle.
     *
     * @param vehicle The vehicle to set.
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Set the flag indicating if the controller has finished its operation.
     *
     * @param isFinished The flag value to set.
     */
    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    /**
     * Set the list of moves/instructions.
     *
     * @param controls The list of moves to set.
     */
    public void setMoves(List<String> moves) {
        return;
    }

    /**
     * Set the index for automatic movement.
     *
     * @param autoMovementIndex The index to set.
     */
    public void setMovementIndex(int autoMovementIndex) {
        return;
    }
}
