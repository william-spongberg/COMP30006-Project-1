package ore;

import java.util.List;
import java.util.Map;

import ch.aplu.jgamegrid.*;

public abstract class Vehicle extends MapObject {
    private List<String> controls = null;
    private int autoMovementIndex = 0;

    public Vehicle(boolean rotatable, String image) {
        super(rotatable, image);
    }

    // move or destroy the ore
    public abstract boolean updateObject(MapObject object);

    /**
     * Check if we can move the pusher into the location
     * 
     * @param location
     * @return
     */
    public boolean canMove(Location location) {
        // Test if try to move into border, rock or clay
        Color c = getBg().getColor(location);
        Rock rock = (Rock) getOneActorAt(location, Rock.class);
        Clay clay = (Clay) getOneActorAt(location, Clay.class);
        Bulldozer bulldozer = (Bulldozer) getOneActorAt(location, Bulldozer.class);
        Excavator excavator = (Excavator) getOneActorAt(location, Excavator.class);
        if (c.equals(borderColor) || rock != null || clay != null || bulldozer != null || excavator != null)
            return false;
        else {
            MapObject object = (MapObject) getOneActorAt(location);
            if (object != null) {
                return updateObject(object);
            }
        }

        return false; // TODO: was set to return true??
    }

    /* getters */
    public List<String> getControls() {
        return controls;
    }

    public int getAutoMovementIndex() {
        return autoMovementIndex;
    }

    /* setters */
    public void setControls(List<String> controls) {
        this.controls = controls;
    }

    public void setAutoMovementIndex(int autoMovementIndex) {
        this.autoMovementIndex = autoMovementIndex;
    }
}
