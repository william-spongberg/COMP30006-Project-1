package ore;

import ch.aplu.jgamegrid.*;

import java.util.List;

public abstract class Vehicle extends Actor {
    private Location nextLocation = null;
    private VehicleController controller = null;

    public Vehicle(String image, Location location, boolean isAuto, List<String> controls, int autoMovementIndex) {
        super(true, image);
        this.setLocation(location);

        if (isAuto) {
            this.controller = new AutomaticController(this, controls, autoMovementIndex);
        } else {
            this.controller = new KeyboardController(this);
        }
    }

    public void move(Location location) {
        if (location != null && canMove(location)) {
            Target curTarget = (Target) gameGrid.getOneActorAt(this.getLocation(), Target.class);
            if (curTarget != null) {
                curTarget.show();
            }
            this.setLocation(location);
        }
    }

    public abstract boolean canMove(Location location);

    public abstract boolean collideWithActor(Actor actor);

    public Location getNextLocation() {
        return this.nextLocation;
    }

    public VehicleController getController() {
        return this.controller;
    }

    public void setNextLocation(Location nextLocation) {
        this.nextLocation = nextLocation;
    }

    public void setController(VehicleController controller) {
        this.controller = controller;
    }
    
}