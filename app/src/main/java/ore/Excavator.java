package ore;

public class Excavator extends Vehicle {
    public Excavator() {
        super(true, "sprites/excavator.png"); // can destroy rocks
    }

    public boolean updateObject(MapObject object) {
        if (object instanceof Rock) {
            Rock rock = (Rock) object;
            rock.update(map);
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
