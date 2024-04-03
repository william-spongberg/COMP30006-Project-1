package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class MapObject extends Actor implements Updateable {

    private Location location;

    private static List<MapObject> allMapObjects = new ArrayList<>();

    // need to discuss how location works here.. we also need to set it in the children, how do we fix?
    public MapObject(boolean isRotatable, String spriteImage, Location location) {
        super(isRotatable, spriteImage);
        this.location = location;
        allMapObjects.add(this);
    }

    @Override
    public Location getLocation() {
        return location;
    }
    @Override
    public void setLocation(Location location) {
        this.location = location;

    }

    public void interact(MapObject otherObject) {
        if (this instanceof Excavator && otherObject instanceof Rock) {
            ((Destroyable) otherObject).destroy();
        }

        if (this instanceof Bulldozer && otherObject instanceof Clay) {
            ((Destroyable) otherObject).destroy();
        }

    }
}