package ore.controller;

import ch.aplu.jgamegrid.Location;
import ore.vehicle.Vehicle;

import java.util.List;

/**
 * The VehicleController interface represents a controller for a vehicle in a
 * simulation.
 */
public interface VehicleController {
    /**
     * Calculate the next location for movement.
     *
     * @return The next location for movement, or null if failed.
     */
    public Location nextMove();

    /* getters */

    /**
     * Get the associated vehicle.
     *
     * @return The associated vehicle.
     */
    public Vehicle getVehicle();

    /**
     * Check if the controller has finished its operation.
     *
     * @return True if the controller has finished, false otherwise.
     */
    public boolean getIsFinished();

    /**
     * Get the list of moves/instructions.
     *
     * @return The list of moes.
     */
    public List<String> getMoves();

    /**
     * Get the index for movement.
     *
     * @return The index for movement.
     */
    public int getMovementIndex();

    /* setters */

    /**
     * Set the associated vehicle.
     *
     * @param vehicle The vehicle to set.
     */
    public void setVehicle(Vehicle vehicle);

    /**
     * Set the flag indicating if the controller has finished its operation.
     *
     * @param isFinished The flag value to set.
     */
    public void setIsFinished(boolean isFinished);

    /**
     * Set the list of moves/instructions.
     *
     * @param controls The list of moves to set.
     */
    public void setMoves(List<String> controls);

    /**
     * Set the index for movement.
     *
     * @param autoMovementIndex The index to set.
     */
    public void setMovementIndex(int autoMovementIndex);
}
