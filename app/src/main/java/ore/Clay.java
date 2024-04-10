package ore;

import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.Actor;

public class Clay extends Actor
{
    public Clay(boolean isRotatable, String filename) {
        super(false, "sprites/clay.png");
    }

    public void destroy() {
        removeSelf();
    }

}



