package ore;

import ch.aplu.jgamegrid.*;

public abstract class Vehicle extends Actor {
    private Location nextLocation = null;
    private VehicleController controller = null;

    public Vehicle(String image, Location location, boolean isAuto) {
        super(true, image);
        this.setLocation(location);
        if (isAuto) {
            this.controller = new AutomaticController();
        } else {
            this.controller = new KeyboardController();
        }
    }
    
    public void move(Location location) {
        if (location != null && canMove(location)) {
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