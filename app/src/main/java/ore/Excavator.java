package ore;

import java.util.List;
import java.util.Map;

import ch.aplu.jgamegrid.*;

public class Excavator extends Vehicle {
    public Excavator() {
        super(true, "sprites/excavator.png"); // can destroy rocks
    }

    public boolean updateObject(MapObject object) {
        if (object instanceof Destroyable) {
            Destroyable destroyable = (Destroyable) object;
            // Try to destroy the object
            return destroyOre(destroyable);
        }
        return false;
    }

    private boolean destroyOre(Destroyable destroyable) {
        if (destroyable instanceof Rock) {
            destroyable.destroy();
            return true;
        }
        return false;
    }
}
