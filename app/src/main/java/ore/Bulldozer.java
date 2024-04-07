package ore;

import java.util.List;
import java.util.Map;

import ch.aplu.jgamegrid.*;

public class Bulldozer extends Vehicle {
    public Bulldozer() {
        super(true, "sprites/bulldozer.png"); // same as excavator except can destroy clay
    }

    // just updating this to be more reflective of what bulldozer "does". Not sure if destroyClay is necessary
    // if the only other object a bulldozer can update is clay.
    public boolean updateObject(MapObject object) {
        if (object instanceof Clay) {
            Clay clay = (Clay) object;
            clay.update();
            return true;
        }
        return false;
    }

    private boolean destroyClay(Destroyable destroyable) {
        if (destroyable instanceof Clay) {
            destroyable.destroy();
            return true;
        }
        return false;
    }
}
