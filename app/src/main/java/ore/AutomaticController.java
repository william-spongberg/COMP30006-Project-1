package ore;

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;

public class AutomaticController extends VehicleController {
    /**
     * Method to move vehicle automatically based on the instructions input from
     * properties file
     */
    public Location autoMoveNext() {
        if (this.getControls() != null && this.getAutoMovementIndex() < this.getControls().size()) {
            String[] currentMove = this.getControls().get(this.getAutoMovementIndex()).split("-");
            String machine = currentMove[0];
            String move = currentMove[1];
            this.setAutoMovementIndex(this.getAutoMovementIndex() + 1);
            
            if (machine.equals("P")) {
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

                Target curTarget = (Target) getOneActorAt(this.getVehicle().getLocation(), Target.class);
                if (curTarget != null) {
                    curTarget.show();
                }

                if (next != null) {
                    return next;
                }

                return null;
            }
        }
    }

    public Location keyPressed(KeyEvent evt) {
        return null;
    }

    public Location keyReleased(KeyEvent evt) {
        return null;
    }
}
