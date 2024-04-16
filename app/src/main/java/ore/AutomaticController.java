package ore;

import ch.aplu.jgamegrid.Location;
import java.util.List;

/**
 * The AutomaticController class is a subclass of VehicleController that
 * represents a controller for a vehicle that can move automatically based on
 * instructions inputted from a properties file.
 */
public class AutomaticController implements VehicleController {
    private Vehicle vehicle = null;
    private boolean isFinished = false;
    private List<String> moves = null;
    private int movementIndex = 0;
    private Location nextLocation = null;

    /**
     * Constructs a new AutomaticController object with the specified vehicle,
     * controls, and autoMovementIndex.
     *
     * @param vehicle           the vehicle to be controlled
     * @param controls          the list of controls/instructions for automatic
     *                          movement
     * @param autoMovementIndex the index of the current control/instruction
     */
    public AutomaticController(Vehicle vehicle, List<String> moves, int movementIndex) {
        this.setVehicle(vehicle);
        this.setMoves(moves);
        this.setMovementIndex(movementIndex);
    }

    /**
     * Moves the vehicle automatically based on the instructions input from the
     * properties file.
     *
     * @return the next location of the vehicle after the automatic movement,
     * or null if the movement is finished or an invalid control is
     * encountered
     */
    public Location nextMove() {
        if (this.getMoves() != null && this.getMovementIndex() < this.getMoves().size()) {
            String[] currentMove = this.getMoves().get(this.getMovementIndex()).split("-");
            String machine = currentMove[0];
            String move = currentMove[1];
            this.setMovementIndex(this.getMovementIndex() + 1);

            if ((machine.equals("P") && this.getVehicle() instanceof Pusher)
                    || (machine.equals("B") && this.getVehicle() instanceof Bulldozer)
                    || (machine.equals("E") && this.getVehicle() instanceof Excavator)) {

                System.out.println("Machine: " + machine + ", Move: " + move);

                if (this.getIsFinished())
                    return null;

                Location next = null;
                switch (move) {
                    case "L":
                        next = this.getVehicle().getLocation().getNeighbourLocation(Location.WEST);
                        this.getVehicle().setDirection(Location.WEST);
                        break;
                    case "U":
                        next = this.getVehicle().getLocation().getNeighbourLocation(Location.NORTH);
                        this.getVehicle().setDirection(Location.NORTH);
                        break;
                    case "R":
                        next = this.getVehicle().getLocation().getNeighbourLocation(Location.EAST);
                        this.getVehicle().setDirection(Location.EAST);
                        break;
                    case "D":
                        next = this.getVehicle().getLocation().getNeighbourLocation(Location.SOUTH);
                        this.getVehicle().setDirection(Location.SOUTH);
                        break;
                }

                return next;
            }
        }
        return null;
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
     * @return The list of moves.
     */
    public List<String> getMoves() {
        return this.moves;
    }

    /**
     * Get the index for automatic movement.
     *
     * @return The index for automatic movement.
     */
    public int getMovementIndex() {
        return this.movementIndex;
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
     * Set the list of moves.
     *
     * @param controls The list of moves to set.
     */
    public void setMoves(List<String> controls) {
        this.moves = controls;
    }

    /**
     * Set the index for movement.
     *
     * @param movementIndex The index to set.
     */
    public void setMovementIndex(int movementIndex) {
        this.movementIndex = movementIndex;
    }
}
