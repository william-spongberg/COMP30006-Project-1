package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class MapEntity extends Actor {

    Location location;
    public MapEntity(boolean isRotatable, String spriteImage, Location location) {
        super(isRotatable, spriteImage);
        this.location = location;
    }
}
