package ore;

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.util.List;

public abstract class VehicleController {
    // attributes
    private Vehicle vehicle;
    private boolean isFinished;
    private List<String> controls = null;
    private int autoMovementIndex = 0;

    // methods
    public abstract Location autoMoveNext();
    public abstract Location keyPressed(KeyEvent evt);
    public abstract Location keyReleased(KeyEvent evt);

    /* getters */
    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public List<String> getControls() {
        return controls;
    }

    public int getAutoMovementIndex() {
        return autoMovementIndex;
    }

    /* setters */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void setControls(List<String> controls) {
        this.controls = controls;
    }

    public void setAutoMovementIndex(int autoMovementIndex) {
        this.autoMovementIndex = autoMovementIndex;
    }
}
