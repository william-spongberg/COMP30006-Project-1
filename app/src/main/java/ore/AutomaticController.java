package ore;

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;

public class AutomaticController extends VehicleController {
    /**
     * Method to move vehicle automatically based on the instructions input from
     * properties file
     */
    public void autoMoveNext() {
        if (vehicle.getControls() != null && vehicle.getAutoMovementIndex() < vehicle.getControls().size()) {
            String[] currentMove = vehicle.getControls().get(vehicle.getAutoMovementIndex()).split("-");
            String machine = currentMove[0];
            String move = currentMove[1];
            vehicle.setAutoMovementIndex(vehicle.getAutoMovementIndex() + 1);
            
            if (machine.equals("P")) {
                if (isFinished)
                    return;

                Location next = null;
                switch (move) {
                    case "L":
                        next = vehicle.getLocation().getNeighbourLocation(Location.WEST);
                        vehicle.setDirection(Location.WEST);
                        break;
                    case "U":
                        next = vehicle.getLocation().getNeighbourLocation(Location.NORTH);
                        vehicle.setDirection(Location.NORTH);
                        break;
                    case "R":
                        next = vehicle.getLocation().getNeighbourLocation(Location.EAST);
                        vehicle.setDirection(Location.EAST);
                        break;
                    case "D":
                        next = vehicle.getLocation().getNeighbourLocation(Location.SOUTH);
                        vehicle.setDirection(Location.SOUTH);
                        break;
                }

                Target curTarget = (Target) getOneActorAt(vehicle.getLocation(), Target.class);
                if (curTarget != null) {
                    curTarget.show();
                }
                if (next != null && vehicle.canMove(next)) {
                    vehicle.setLocation(next);
                }
                refresh(); // TODO: refreshes gameGrid
            }
        }
    }
}
