package ore;

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;

public class KeyboardController extends VehicleController {

    /**
     * The method is automatically called by the framework when a key is pressed.
     * Based on the pressed key, the pusher will change the direction and move
     * 
     * @param evt
     * @return
     */
    public boolean keyPressed(KeyEvent evt) {
        if (isFinished)
            return true;

        Location next = null;
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                next = vehicle.getLocation().getNeighbourLocation(Location.WEST);
                vehicle.setDirection(Location.WEST);
                break;
            case KeyEvent.VK_UP:
                next = vehicle.getLocation().getNeighbourLocation(Location.NORTH);
                vehicle.setDirection(Location.NORTH);
                break;
            case KeyEvent.VK_RIGHT:
                next = vehicle.getLocation().getNeighbourLocation(Location.EAST);
                vehicle.setDirection(Location.EAST);
                break;
            case KeyEvent.VK_DOWN:
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
            updateLogResult();
        }
        refresh();

        return true;
    }

    public boolean keyReleased(KeyEvent evt) {
        return true;
    }
}
