package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


// MapEntities are updatable, MapObjects are not.
public abstract class MapEntity extends Actor implements Updateable {

    Location location;
    public MapEntity(boolean isRotatable, String spriteImage, Location location) {
        super(isRotatable, spriteImage);
        this.location = location;
    }

    public MapGrid update(MapGrid map) {
        if (this instanceof Rock || this instanceof Clay) {
            removeSelf();
            return map;
        }

        return map;
    }
}
