package ore;

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;

public class KeyboardController extends VehicleController {
    public void autoMoveNext() {
        return;
    }
    /**
     * The method is automatically called by the framework when a key is pressed.
     * Based on the pressed key, the pusher will change the direction and move
     * 
     * @param evt
     * @return
     */
    public boolean keyPressed(KeyEvent evt) {
        if (this.getIsFinished())
            return true;

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

        Target curTarget = (Target) getOneActorAt(this.getVehicle().getLocation(), Target.class);
        if (curTarget != null) {
            curTarget.show();
        }

        if (next != null && this.getVehicle().canMove(next)) {
            this.getVehicle().setLocation(next);
            updateLogResult();
        }
        refresh();

        return true;
    }

    public boolean keyReleased(KeyEvent evt) {
        return true;
    }
}
