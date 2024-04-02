package ore;

import java.util.List;
import java.util.Map;

import ch.aplu.jgamegrid.*;

public class Bulldozer extends Vehicle {
    public Bulldozer() {
        super(true, "sprites/bulldozer.png"); // same as excavator except can destroy clay
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
        if (destroyable instanceof Clay) {
            destroyable.destroy();
            return true;
        }
        return false;
    }
}
