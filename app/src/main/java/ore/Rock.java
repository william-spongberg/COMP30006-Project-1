package ore;

import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.Actor;
public class Rock extends Actor {

    public Rock() {
        super(false, "sprites/rock.png");
    }

    public void destroy() {
        removeSelf();
    }


}
