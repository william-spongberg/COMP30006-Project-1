package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class MapEntity extends Actor {
    public MapEntity(boolean isRotatable, String filename) {
        super(isRotatable, filename);
    }
}
