package ore;

import java.util.List;
import java.util.Map;

import ch.aplu.jgamegrid.*;

public class Excavator extends Vehicle {
    public Excavator() {
        super(true, "sprites/excavator.png"); // can destroy rocks
    }

    public boolean updateObject(MapObject object) {
        if (object instanceof Rock) {
            Rock rock = (Rock) object;
            rock.update();
            return true;
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
