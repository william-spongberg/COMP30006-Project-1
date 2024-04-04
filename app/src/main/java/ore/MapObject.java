package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class MapObject extends Actor implements Updateable {

    private Location location;

    private static List<MapObject> allMapObjects = new ArrayList<>();

    // location will be set and handled by the map. these objects are fine as is.
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
            // to implement when hashmap is in
        }

        if (this instanceof Bulldozer && otherObject instanceof Clay) {
            // to implement when hashmap is in
        }

    }
}