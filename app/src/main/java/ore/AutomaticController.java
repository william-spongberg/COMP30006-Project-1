package ore;

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * The AutomaticController class is a subclass of VehicleController that
 * represents a controller for a vehicle that can move automatically based on
 * instructions inputted from a properties file.
 */
public class AutomaticController extends VehicleController {

    /**
     * Constructs a new AutomaticController object with the specified vehicle,
     * controls, and autoMovementIndex.
     *
     * @param vehicle           the vehicle to be controlled
     * @param controls          the list of controls/instructions for automatic
     *                          movement
     * @param autoMovementIndex the index of the current control/instruction
     */
    public AutomaticController(Vehicle vehicle, List<String> controls, int autoMovementIndex) {
        this.setVehicle(vehicle);
        this.setControls(controls);
        this.setAutoMovementIndex(autoMovementIndex);
    }

    /**
     * Moves the vehicle automatically based on the instructions input from the
     * properties file.
     *
     * @return the next location of the vehicle after the automatic movement,
     *         or null if the movement is finished or an invalid control is
     *         encountered
     */
    public Location autoMoveNext() {
        if (this.getControls() != null && this.getAutoMovementIndex() < this.getControls().size()) {
            String[] currentMove = this.getControls().get(this.getAutoMovementIndex()).split("-");
            String machine = currentMove[0];
            String move = currentMove[1];
            this.setAutoMovementIndex(this.getAutoMovementIndex() + 1);

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

                if (next != null) {
                    return next;
                }
            }
        }
        return null;
    }

    /**
     * Handles the manual movement of the vehicle.
     *
     * @return null as manual controls are not supported in this controller
     */
    public Location manualMoveNext() {
        return null;
    }

    /**
     * Handles the key pressed event.
     *
     * @param evt the KeyEvent object representing the key press event
     * @return false as manual controls are not supported in this controller
     */
    public boolean keyPressed(KeyEvent evt) {
        return false;
    }

    /**
     * Handles the key released event.
     *
     * @param evt the KeyEvent object representing the key release event
     * @return false as manual controls are not supported in this controller
     */
    public boolean keyReleased(KeyEvent evt) {
        return false;
    }
}
