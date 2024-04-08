package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public abstract class MapObject implements Updateable {

    private final OreSim.ElementType type;

    // location will be set and handled by the map. these objects are fine as is.
    public MapObject(OreSim.ElementType type) {

        this.type = type;
    }

    public OreSim.ElementType getType() {
        return type;
    }

    /*
    public void interact(MapObject otherObject) {
        if (this instanceof Excavator && otherObject instanceof Rock) {
            // to implement when hashmap is in
        }

        if (this instanceof Bulldozer && otherObject instanceof Clay) {
            // to implement when hashmap is in
        }

    }
    */
}