package ore;

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * The abstract class representing a vehicle controller.
 * It implements the GGKeyListener interface.
 */
public abstract class VehicleController implements GGKeyListener {
    private Vehicle vehicle;
    private boolean isFinished;
    private List<String> controls = null;
    private int autoMovementIndex = 0;

    /**
     * Abstract method to calculate the next location for automatic movement.
     *
     * @return The next location for automatic movement, or null if failed.
     */
    public abstract Location autoMoveNext();

    /**
     * Abstract method to get the next location for manual movement.
     *
     * @return The next location for manual movement, or null if failed.
     */
    public abstract Location manualMoveNext();

    /**
     * Abstract method to handle key press events.
     *
     * @param evt The key event.
     * @return Whether the key was pressed.
     */
    public abstract boolean keyPressed(KeyEvent evt);

    /**
     * Abstract method to handle key release events.
     *
     * @param evt The key event.
     * @return Whether the key was released.
     */
    public abstract boolean keyReleased(KeyEvent evt);

    /* getters */

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
        return isFinished;
    }

    /**
     * Get the list of controls.
     *
     * @return The list of controls.
     */
    public List<String> getControls() {
        return controls;
    }

    /**
     * Get the index for automatic movement.
     *
     * @return The index for automatic movement.
     */
    public int getAutoMovementIndex() {
        return autoMovementIndex;
    }

    /* setters */

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
     * Set the list of controls.
     *
     * @param controls The list of controls to set.
     */
    public void setControls(List<String> controls) {
        this.controls = controls;
    }

    /**
     * Set the index for automatic movement.
     *
     * @param autoMovementIndex The index to set.
     */
    public void setAutoMovementIndex(int autoMovementIndex) {
        this.autoMovementIndex = autoMovementIndex;
    }
}
