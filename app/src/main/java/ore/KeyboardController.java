package ore;

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;

/**
 * The KeyboardController class is a subclass of VehicleController that handles
 * user input from the keyboard to control a vehicle in the game.
 */
public class KeyboardController extends VehicleController {

    /**
     * Constructs a new KeyboardController object with the specified vehicle.
     *
     * @param vehicle the vehicle to be controlled
     */
    public KeyboardController(Vehicle vehicle) {
        this.setVehicle(vehicle);
    }

    /**
     * This method is automatically called by the framework when a key is pressed.
     * Based on the pressed key, the pusher will change the direction and move.
     *
     * @param evt the KeyEvent object representing the key press event
     * @return the next location to move to, or null if no valid location
     */
    public Location keyPressed(KeyEvent evt) {
        if (this.getIsFinished())
            return null;

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
            // TODO: updateLogResult();
            return next;
        }

        return null;
    }

    /**
     * Handles the automatic movement of the vehicle.
     * 
     * @return null as automatic controls are not supported in this controller
     */
    public Location autoMoveNext() {
        return null;
    }

    /**
     * Handles the key released event.
     *
     * @param evt the KeyEvent object representing the key release event
     * @return null as key releases are not supported in this controller
     */
    public Location keyReleased(KeyEvent evt) {
        return null;
    }
}
